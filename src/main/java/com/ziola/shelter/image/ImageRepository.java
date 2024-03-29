package com.ziola.shelter.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
