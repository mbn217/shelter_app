package com.ziola.shelter.animals.controllers;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.logic.HowManyPlaceLeft;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.animals.service.SaveAnimalService;
import com.ziola.shelter.aws.domain.Image;
import com.ziola.shelter.aws.repository.ImageRepository;
import com.ziola.shelter.aws.service.BucketsService;
import com.ziola.shelter.aws.service.impl.ImageServiceImpl;
import com.ziola.shelter.emails.service.SendingEmailToWorkersService;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import com.ziola.shelter.util.ConvertingMultipartToFile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class AddAnimalsController {

    private int numberOfFreePlacesToSendEmails = 3;
    private HowManyPlaceLeft howManyPlaceLeft;
    private SendingEmailToWorkersService sendingEmailToWorkersService;
    private TaskExecutor taskExecutor;
    private SaveAnimalService saveAnimalService;
    private AnimalRepository animalRepository;
    private BucketsService bucketsService;
    private ConverterDtoAnimalEntity converterDtoAnimalEntity;
    private ConvertingMultipartToFile convertingMultipartToFile;
    private final static String BUCKETNAME = "shelteriploadimages";
    private ImageRepository imageRepository;
    private ImageServiceImpl imageService;

    @Autowired
    public AddAnimalsController(HowManyPlaceLeft howManyPlaceLeft, AnimalRepository animalRepository,
                                SendingEmailToWorkersService sendingEmailToWorkersService, TaskExecutor taskExecutor,
                                SaveAnimalService saveAnimalService, BucketsService bucketsService,
                                ConverterDtoAnimalEntity converterDtoAnimalEntity, ConvertingMultipartToFile convertingMultipartToFile,
                                ImageRepository imageRepository, ImageServiceImpl imageService) {
        this.howManyPlaceLeft = howManyPlaceLeft;
        this.sendingEmailToWorkersService = sendingEmailToWorkersService;
        this.taskExecutor = taskExecutor;
        this.saveAnimalService = saveAnimalService;
        this.animalRepository = animalRepository;
        this.bucketsService = bucketsService;
        this.converterDtoAnimalEntity = converterDtoAnimalEntity;
        this.convertingMultipartToFile = convertingMultipartToFile;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    @GetMapping("/addAnimal")
    public String getNewAnimalForm(Model model) {
        AnimalDTO newAnimalDto = new AnimalDTO();
        int numberOfFreePlaces = howManyPlaceLeft.freePlacesLeft();
        model.addAttribute("newAnimalDto", newAnimalDto);
        return checkIfCanAddAnimal(model, numberOfFreePlaces);
    }

    @PostMapping("/processNewAnimal")
    public String processNewAnimalForm(@Valid @ModelAttribute("newAnimalDto") AnimalDTO newAnimalDto,
                                       BindingResult result, @RequestPart("file") MultipartFile image, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addingAnimal";
        }
        Animal newAnimal = converterDtoAnimalEntity.convertToEntity(newAnimalDto);
        Image newImage = checkIfImageExists(newAnimalDto, newAnimal);
        saveAnimalService.saveAnimal(newAnimal, findNameOfLoggedUser(), newImage);
        taskExecutor.execute(() -> sendingEmailToWorkersService.checkIfEmailCanBeSent(howManyPlaceLeft.freePlacesLeft(),
                numberOfFreePlacesToSendEmails));
        if (!image.isEmpty()) {
            taskExecutor.execute(() -> uploadFile(image, newAnimal));
        }
        redirectAttributes.addFlashAttribute("flash.message", "Zwierzę dodane!");
        return "redirect:/addAnimal";
    }

    private Image checkIfImageExists(@ModelAttribute("newAnimalDto") @Valid AnimalDTO newAnimalDto, Animal newAnimal) {
        Image newImage;
        if (newAnimalDto.getLinkToImage() != null && !newAnimalDto.getLinkToImage().isEmpty()) {
            Animal tempAnimal = animalRepository.findById(newAnimal.getId()).orElseThrow();
            newImage = tempAnimal.getImage();
        } else {
            newImage = imageService.createNewImage(newAnimal);
            imageRepository.save(newImage);
        }
        return newImage;
    }

    private String checkIfCanAddAnimal(Model model, int numberOfFreePlaces) {
        if (numberOfFreePlaces <= 0) {
            model.addAttribute("noFreePlaceMessage", "Obecnie nie mamy wolnych miejsc. Do zobaczenia wkrótce!");
            return "addingAnimal";
        } else {
            model.addAttribute("freePlacesExist", numberOfFreePlaces);
            return "addingAnimal";
        }
    }

    private String findNameOfLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private void uploadFile(MultipartFile multipartFile, Animal animal) {
        try {
            File file = convertingMultipartToFile.convertMultiPartToFile(multipartFile);
            String fileName = convertingMultipartToFile.generateFileName(animal);
            bucketsService.uploadFile(BUCKETNAME, fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
