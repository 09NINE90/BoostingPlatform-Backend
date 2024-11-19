package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    @GetMapping("/mainPage")
    public ModelAndView getMainPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main-page");
        return modelAndView;
    }

    @GetMapping("/getSignupForm")  // todo сделать форму регистрации
    public ModelAndView getSignupForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup-form");
        return modelAndView;
    }

}
