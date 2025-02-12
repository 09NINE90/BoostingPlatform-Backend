package ru.platform.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.platform.config.custom_annotation.RoleRequired;
import ru.platform.entity.UserEntity;
import ru.platform.service.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final IUserService service;

    @GetMapping("/getAllUsers")
    @Schema(
            description = "Получение всех пользователей"
    )
    @RoleRequired({"ROLE_ADMIN"})
    public List<UserEntity> getAllUsers(){
        return service.getAllUsers();
    }

}
