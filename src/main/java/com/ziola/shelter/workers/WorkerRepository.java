package com.ziola.shelter.workers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    Worker findById(int id);

    Worker findByEmail(String email);
}
