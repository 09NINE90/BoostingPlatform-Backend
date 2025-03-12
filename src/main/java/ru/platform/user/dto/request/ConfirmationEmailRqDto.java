package ru.platform.user.dto.request;

import lombok.Data;

@Data
public class ConfirmationEmailRqDto {
    private String email;
    private String password;
    private String confirmationCode;
}
