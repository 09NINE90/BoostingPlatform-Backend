package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.platform.entity.UserEntity;
import ru.platform.service.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final IUserService service;

    @GetMapping("/getAllUsers")
    public List<UserEntity> getAllUsers(){
        return service.getAllUsers();
    }

}
