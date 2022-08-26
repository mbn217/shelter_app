package com.ziola.shelter.animals;

import com.ziola.shelter.animals.Animal;
import com.ziola.shelter.animals.AnimalDTO;
import com.ziola.shelter.animals.AnimalRepository;
import com.ziola.shelter.animals.AnimalService;
import com.ziola.shelter.image.Image;
import com.ziola.shelter.image.ImageRepository;
import com.ziola.shelter.aws.BucketsService;
import com.ziola.shelter.image.ImageServiceImpl;
import com.ziola.shelter.emails.service.SendingEmailToWorkersService;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import com.ziola.shelter.util.ConvertingMultipartToFile;
import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.repository.WorkerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {
    private static final int MAXIMUM_ANIMALS = 10;
    private final static String BUCKET_NAME = "shelterappphotos";
    private final static int NUMBER_OF_FREE_PLACES_TO_SEND_EMAILS = 3;
    private final AnimalRepository animalRepository;
    private final WorkerRepository workerRepository;
    private final SendingEmailToWorkersService sendingEmailToWorkersService;
    private final TaskExecutor taskExecutor;
    private final BucketsService bucketsService;
    private final ConverterDtoAnimalEntity converterDtoAnimalEntity;
    private final ConvertingMultipartToFile convertingMultipartToFile;
    private final ImageRepository imageRepository;
    private final ImageServiceImpl imageService;


    public void saveAnimal(Animal animalToSave, Image image) {
        String emailOfLoggedUser = findEmailOfLoggedUser();
        Worker workerWhoIsAdding = workerRepository.findByEmail(emailOfLoggedUser);
        animalToSave.setWorker(workerWhoIsAdding);
        animalToSave.setImage(image);
        animalRepository.save(animalToSave);
    }

    public int freePlacesLeft() {
        return MAXIMUM_ANIMALS - animalRepository.findAll().size();
    }

    @Override
    public void takeDTOConvertAndSave(AnimalDTO newAnimalDto, MultipartFile image) {
        Animal newAnimal = converterDtoAnimalEntity.convertToEntity(newAnimalDto);
        boolean doesImageExist = checkIfImageExists(newAnimalDto);
        Image newImage;
        if (doesImageExist) newImage = getExistingImage(newAnimal);
        else newImage = saveAndReturnImage(image, newAnimal);
        saveAnimal(newAnimal, newImage);
        taskExecutor.execute(() -> sendingEmailToWorkersService.checkIfEmailCanBeSent(freePlacesLeft(),
                NUMBER_OF_FREE_PLACES_TO_SEND_EMAILS));
    }

    @Override
    public AnimalDTO findAnimalByIdAndConvertToDTO(int animalId) {
        Animal tempAnimal = animalRepository.findById(animalId);
        return converterDtoAnimalEntity.convertToDto(tempAnimal);
    }

    @Override
    public void findAndDeleteAnimal(int animalId) {
        Animal animal = animalRepository.findById(animalId);
        String[] temp = animal.getImage().getLinkToImage().split("https://s3.eu-central-1.amazonaws.com/shelteriploadimages/");
        String nameOfImage = temp[1];
        if (bucketsService.listHasObject(BUCKET_NAME, nameOfImage)) {
            bucketsService.deleteAnObject(BUCKET_NAME, animal);
        }
        imageRepository.delete(animal.getImage());
        animalRepository.deleteById(animalId);
    }

    @Override
    public List<AnimalDTO> findAllAnimalsBySpecieAndCity(String cityOfAnimal) {
        return animalRepository.findAll().stream()
                .filter(animal -> animal.getCity().equals(cityOfAnimal))
                .map(converterDtoAnimalEntity::convertToDtoWithId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<AnimalDTO> findAllAnimalsBySpecie(String specie) {
        List<AnimalDTO> listOfAllAnimalsBySpecie = new ArrayList<>();
        if (specie.equals("all")) {
            animalRepository.findAll()
                    .forEach(animal -> listOfAllAnimalsBySpecie.add(converterDtoAnimalEntity.convertToDtoWithId(animal)));
        } else {
            animalRepository.findBySpecie(specie)
                    .forEach(animal -> listOfAllAnimalsBySpecie.add(converterDtoAnimalEntity.convertToDtoWithId(animal)));
        }
        return listOfAllAnimalsBySpecie;
    }

    @Override
    public List<String> collectCitiesToList() {
        return animalRepository.findAll().stream()
                .map(Animal::getCity).distinct().collect(java.util.stream.Collectors.toList());
    }

    private Image getExistingImage(Animal newAnimal) {
        Animal tempAnimal = animalRepository.findById(newAnimal.getId()).orElseThrow();
        return tempAnimal.getImage();
    }

    private Image saveAndReturnImage(MultipartFile image, Animal newAnimal) {
        Image newImage = imageService.createNewImage(newAnimal);
        imageRepository.save(newImage);
        if (!image.isEmpty()) {
            taskExecutor.execute(() -> uploadFile(image, newAnimal));
        }
        return newImage;
    }

    private String findEmailOfLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private void uploadFile(MultipartFile multipartFile, Animal animal) {
        try {
            File file = convertingMultipartToFile.convertMultiPartToFile(multipartFile);
            String fileName = convertingMultipartToFile.generateFileName(animal);
            bucketsService.uploadFile(BUCKET_NAME, fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfImageExists(@ModelAttribute("newAnimalDto") @Valid AnimalDTO newAnimalDto) {
        return newAnimalDto.getLinkToImage() != null && !newAnimalDto.getLinkToImage().isEmpty();
    }
}
