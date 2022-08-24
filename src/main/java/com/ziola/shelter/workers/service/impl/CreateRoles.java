package com.ziola.shelter.workers.service.impl;

import com.ziola.shelter.workers.domain.Role;
import com.ziola.shelter.workers.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRoles {

    private RoleRepository roleRepository;

    @Autowired
    public CreateRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public CreateRoles() {
    }

    public void createRolesAdminAndUser() {

        Role admin = new Role();
        Role pedal = new Role();

        if (roleRepository.findByRole("ADMIN") == null) roleRepository.save(admin);
        if (roleRepository.findByRole("pedal") == null) roleRepository.save(pedal);
    }
}
