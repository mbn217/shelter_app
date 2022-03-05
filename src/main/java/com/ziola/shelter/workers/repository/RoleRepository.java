package com.ziola.shelter.workers.repository;

import com.ziola.shelter.workers.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Role findByRole(String role);
}
