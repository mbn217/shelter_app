package com.ziola.shelter.workers.dto;

import com.ziola.shelter.validation.annotation.PasswordMatches;
import com.ziola.shelter.validation.annotation.ValidEmail;
import com.ziola.shelter.workers.domain.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@PasswordMatches
public class WorkerDTO {

    @NotNull(message = "{worker.domain.null.error}")
    @NotEmpty(message = "{worker.domain.null.error}")
    @ValidEmail
    private String email;
    @NotNull(message = "{worker.domain.null.error}")
    @NotEmpty(message = "{worker.domain.null.error}")
    private String password;
    private String matchingPassword;
    @NotNull(message = "{worker.domain.null.error}")
    @NotEmpty(message = "{worker.domain.null.error}")
    private String name;
    @NotNull(message = "{worker.domain.null.error}")
    @NotEmpty(message = "{worker.domain.null.error}")
    private String lastName;

    private int isAdmin;
    private boolean active;

    private Role role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WorkerDTO() {
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
