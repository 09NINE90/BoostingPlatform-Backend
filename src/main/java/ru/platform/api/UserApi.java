package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.platform.dto.CustomUserDetails;
import ru.platform.dto.UserDTO;
import ru.platform.entity.UserEntity;
import ru.platform.entity.enums.ERoles;
import ru.platform.service.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final IUserService service;

    @GetMapping("/mainPage")
    public ModelAndView getMainPage(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails.getRole().equals(ERoles.ADMIN.getTitle())){
//            modelAndView.setViewName("admin-services");
            modelAndView.setViewName("add-game-form");
//            modelAndView.setViewName("services");


        }
        else {
            modelAndView.setViewName("services");
        }
        return modelAndView;

    }

    @GetMapping("/getSignupForm")
    public ModelAndView getSignupForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup-form");
        return modelAndView;
    }

    @GetMapping("/getAllUsers")
    public List<UserEntity> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/createUser")
    public ResponseEntity<Void> userSave(@RequestBody UserDTO user){
        service.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/user/mainPage").build();
    }

}
