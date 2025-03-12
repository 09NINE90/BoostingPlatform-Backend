package ru.platform.user.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
import ru.platform.user.dto.response.AuthRsDto;
import ru.platform.user.dto.response.ConfirmationRsDto;
import ru.platform.user.service.IAuthService;
import ru.platform.user.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final IUserService userService;
    private final IAuthService authService;

    @PostMapping("/signUp")
    @Schema(description = "Создание пользователя")
    public ResponseEntity<ConfirmationRsDto> signUp(@RequestBody SignupUserRqDto user) {
        ConfirmationRsDto result = userService.createUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirmSignUp")
    @Schema(description = "Подтверждение регистрации пользователя")
    public ResponseEntity<AuthRsDto> confirmSignUp(@RequestBody ConfirmationEmailRqDto confirmation) {
        AuthRsDto result = userService.checkConfirmationSignUp(confirmation);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthRsDto> signIn(@RequestBody LoginUserRqDto user){
        AuthRsDto result = authService.trySignup(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/forgotPassword")
    @Schema(description = "Запрос кода для восстановления пароля")
    public ResponseEntity<ConfirmationRsDto> forgotPassword(@RequestBody ConfirmationEmailRqDto confirmation){
        ConfirmationRsDto result = userService.forgotPassword(confirmation);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirmPasswordRecovery")
    @Schema(description = "Подтверждение кода для смены пароля")
    public ResponseEntity<ConfirmationRsDto> confirmPasswordRecovery(@RequestBody ConfirmationEmailRqDto confirmation){
        ConfirmationRsDto result = userService.confirmPasswordRecovery(confirmation);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeUserPassword")
    @Schema(description = "Запрос на смену пароля")
    public ResponseEntity<AuthRsDto> changeUserPassword(@RequestBody ConfirmationEmailRqDto confirmation){
        AuthRsDto result = userService.changeUserPassword(confirmation);
        return ResponseEntity.ok(result);
    }

}
