package com.ziola.shelter.aws.repository;

import com.ziola.shelter.aws.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
