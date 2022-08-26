package com.ziola.shelter.animals;

import com.ziola.shelter.animals.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface AnimalRepository extends JpaRepository<Animal, Integer>{

	Animal findById(int id);

	List<Animal> findBySpecie(String specieString);

	List<Animal> findByCityAndSpecie(String city, String specie);
}
