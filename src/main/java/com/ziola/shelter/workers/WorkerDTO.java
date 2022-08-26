package com.ziola.shelter.workers;

import com.ziola.shelter.role.Role;
import com.ziola.shelter.validation.annotation.PasswordMatches;
import com.ziola.shelter.validation.annotation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
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


}
