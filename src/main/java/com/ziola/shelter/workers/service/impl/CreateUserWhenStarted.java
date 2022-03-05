package com.ziola.shelter.workers.service.impl;

import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.repository.RoleRepository;
import com.ziola.shelter.workers.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserWhenStarted {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;
    private WorkerRepository workerRepository;

    @Autowired
    public CreateUserWhenStarted(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, WorkerRepository workerRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.workerRepository = workerRepository;
    }

    public void createAdmin() {
        if (workerRepository.findByEmail("a@a.pl") == null) {
            Worker adminWorker = new Worker();
            adminWorker.setActive(true);
            adminWorker.setEmail("a@a.pl");
            adminWorker.setLastName("b");
            adminWorker.setName("a");
            adminWorker.setPassword(bCryptPasswordEncoder.encode("a"));
            adminWorker.setRole(roleRepository.findByRole("ADMIN"));
            workerRepository.save(adminWorker);
        }
    }
}
