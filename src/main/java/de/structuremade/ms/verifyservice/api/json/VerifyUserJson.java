package de.structuremade.ms.verifyservice.api.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class VerifyUserJson {

    @NotEmpty(message = "Code is required")
    private String code;
    @NotEmpty(message = "Password is required")
    private String password;
}
