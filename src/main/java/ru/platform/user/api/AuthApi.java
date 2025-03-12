package ru.platform.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthRsDto;
import ru.platform.user.dto.response.SignupRsDto;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final IUserService userService;
    private final IAuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<SignupRsDto> signUp(@RequestBody SignupUserRqDto user) {
        SignupRsDto result = userService.createUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirmSignUp")
    public ResponseEntity<AuthRsDto> confirmSignUp(@RequestBody ConfirmationEmailRqDto confirmation) {
        AuthRsDto result = userService.checkConfirmationSignUp(confirmation);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthRsDto> signIn(@RequestBody LoginUserRqDto user){
        AuthRsDto result = authService.trySignup(user);
        return ResponseEntity.ok(result);
    }

}
