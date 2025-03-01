package ru.platform.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthResponse;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final IUserService userService;
    private final IAuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignupUserRqDto user) {
        AuthResponse result = userService.createUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginUserRqDto user){
        AuthResponse result = authService.trySignup(user);
        return ResponseEntity.ok(result);
    }

}
