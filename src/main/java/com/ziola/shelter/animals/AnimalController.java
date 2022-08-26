package com.ziola.shelter.animals;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/addAnimal")
    public String getNewAnimalForm(Model model) {
        AnimalDTO newAnimalDto = new AnimalDTO();
        int numberOfFreePlaces = animalService.freePlacesLeft();
        if (numberOfFreePlaces <= 0) {
            model.addAttribute("noFreePlaceMessage", "Obecnie nie mamy wolnych miejsc. Do zobaczenia wkrótce!");
        } else {
            model.addAttribute("freePlacesExist", numberOfFreePlaces);
            model.addAttribute("newAnimalDto", newAnimalDto);
        }
        return "addingAnimal";
    }

    @PostMapping("/processNewAnimal")
    public String processNewAnimalForm(@Valid @ModelAttribute("newAnimalDto") AnimalDTO newAnimalDto,
                                       BindingResult result, @RequestPart("file") MultipartFile image, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addingAnimal";
        }
        animalService.takeDTOConvertAndSave(newAnimalDto, image);
        redirectAttributes.addFlashAttribute("flash.message", "Zwierzę dodane!");
        return "redirect:/addAnimal";
    }

    @GetMapping("/seeDetails/animal/{id}")
    public String seeDetailsAnimal(@PathVariable("id") int animalId, Model model) {
        AnimalDTO animalDTO = animalService.findAnimalByIdAndConvertToDTO(animalId);
        model.addAttribute("animal", animalDTO);
        return "animalsDetails";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/deleteAnimal/{id}")
    public String deleteAnimalById(@PathVariable("id") int animalId, RedirectAttributes redirectAttributes) {
        animalService.findAndDeleteAnimal(animalId);
        redirectAttributes.addFlashAttribute("animalDeleted", "Zwierze zostało usunięte z bazy");
        return "redirect:/showAllAnimals/all";
    }

    @GetMapping("/editDetails/animal/{id}")
    public String editDog(@PathVariable("id") int animalId, Model model) {
        AnimalDTO animalDTO = animalService.findAnimalByIdAndConvertToDTO(animalId);
        model.addAttribute("newAnimalDto", animalDTO);
        return "addingAnimal";
    }
    @GetMapping("/showAllAnimals/{specie}")
    public String showAllAnimals(@PathVariable("specie") String specie, Model model) {
        List<AnimalDTO> allAnimalsDto = animalService.findAllAnimalsBySpecie(specie);
        model.addAttribute("allAnimals", allAnimalsDto);
        model.addAttribute("cities", animalService.collectCitiesToList());
        if (animalService.freePlacesLeft() == 10) {
            model.addAttribute("emptyShelterFlash", "Hotel jest pusty. Można dodać zwierzaka");
        }
        return "showAllAnimals";
    }

    @GetMapping("/showAllAnimals/{specie}/{cities}")
    public String showAnimalsByCity(@PathVariable("specie") String specie, @PathVariable("cities") String cityOfAnimal, Model model) {
        List<AnimalDTO> allAnimalsDto = animalService.findAllAnimalsBySpecieAndCity(cityOfAnimal);
        model.addAttribute("cities", animalService.collectCitiesToList());
        model.addAttribute("allAnimals", allAnimalsDto);
        return "showAllAnimals";
    }
}
