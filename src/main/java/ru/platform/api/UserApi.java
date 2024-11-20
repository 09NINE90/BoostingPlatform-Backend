package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.platform.entity.UserEntity;
import ru.platform.service.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final IUserService service;

    @GetMapping("/mainPage")
    public ModelAndView getMainPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main-page");
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

}
