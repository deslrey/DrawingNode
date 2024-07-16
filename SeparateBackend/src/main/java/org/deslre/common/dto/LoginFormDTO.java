package org.deslre.common.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String username;
    private String password;
    private String verificationCode;
}
