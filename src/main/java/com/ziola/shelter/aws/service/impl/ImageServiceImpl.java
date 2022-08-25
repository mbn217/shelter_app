package com.ziola.shelter.aws.service.impl;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.aws.domain.Image;
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
