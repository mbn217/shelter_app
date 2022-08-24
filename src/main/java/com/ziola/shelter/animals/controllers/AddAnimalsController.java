package com.ziola.shelter.animals.controllers;

import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@RequiredArgsConstructor
@Controller
public class AddAnimalsController {

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
}
