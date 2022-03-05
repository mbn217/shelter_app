package com.ziola.shelter.workers.service.impl;

import com.ziola.shelter.workers.domain.Role;
import com.ziola.shelter.workers.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
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

        Role admin = new Role(1, "ADMIN");
        Role user = new Role(2, "USER");

        if (roleRepository.findByRole("ADMIN") == null) roleRepository.save(admin);
        if (roleRepository.findByRole("USER") == null) roleRepository.save(user);
    }
}
