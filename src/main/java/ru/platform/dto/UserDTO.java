package ru.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String roles;
    private long secondId;
    private String username;
    private String password;
    private String rating;
    private int ordersCount;
    private LocalDate createdAt;
    private LocalDate lastActivityAt;
}
