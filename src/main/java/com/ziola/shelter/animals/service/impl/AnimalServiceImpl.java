package com.ziola.shelter.animals.service.impl;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.animals.service.AnimalService;
import com.ziola.shelter.aws.domain.Image;
import com.ziola.shelter.aws.repository.ImageRepository;
import com.ziola.shelter.aws.service.BucketsService;
import com.ziola.shelter.aws.service.impl.ImageServiceImpl;
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

@RequiredArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {
  private static final int MAXIMUM_ANIMALS = 10;
  private final static String BUCKET_NAME = "shelteriploadimages";
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
    Worker workerWhoIsAdding = workerRepository.findByEmail(findNameOfLoggedUser());
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
    Image newImage = checkIfImageExists(newAnimalDto, newAnimal);
    saveAnimal(newAnimal, newImage);
    taskExecutor.execute(() -> sendingEmailToWorkersService.checkIfEmailCanBeSent(freePlacesLeft(),
            NUMBER_OF_FREE_PLACES_TO_SEND_EMAILS));
    if (!image.isEmpty()) {
      taskExecutor.execute(() -> uploadFile(image, newAnimal));
    }
  }

  private String findNameOfLoggedUser() {
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
}
