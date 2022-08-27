package com.ziola.shelter.workers;

import com.ziola.shelter.role.Role;
import com.ziola.shelter.validation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class WorkerDTOEditing {

    private Integer id;
    @NotNull(message = "{worker.domain.null.error}")
    @NotEmpty(message = "{worker.domain.null.error}")
    @ValidEmail
    private String email;
    @NotNull(message = "{worker.domain.null.error}")
    @NotEmpty(message = "{worker.domain.null.error}")
    private String name;
    @NotNull(message = "{worker.domain.null.error}")
    @NotEmpty(message = "{worker.domain.null.error}")
    private String lastName;
    private int isAdmin;
    private boolean active;
    private Role role;
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
