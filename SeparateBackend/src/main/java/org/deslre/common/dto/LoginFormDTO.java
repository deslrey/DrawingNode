package org.deslre.common.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String userName;
    private String passWord;
    private String verificationCode;
}
