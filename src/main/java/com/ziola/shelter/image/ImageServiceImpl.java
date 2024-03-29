package com.ziola.shelter.image;

import com.ziola.shelter.animals.Animal;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl {

    public Image createNewImage(Animal animal) {
        String link = "https://s3.eu-central-1.amazonaws.com/shelterappphotos/" + animal.getCity()+ animal.getName()+animal.getAge();
        Image image = new Image();
        image.setLinkToImage(link);
        return image;
    }
}
