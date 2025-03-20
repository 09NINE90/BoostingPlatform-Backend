package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static ru.platform.LocalConstants.Api.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = AUTH_TAG_NAME, description = AUTH_TAG_DESCRIPTION)
public class AuthApi {

    private final IUserService userService;
    private final IAuthService authService;

    @PostMapping("/signUp")
    @Operation(summary = "Запрос на регистрацию пользователя")
    public ResponseEntity<ConfirmationRsDto> signUp(@RequestBody SignupUserRqDto user) {
        ConfirmationRsDto result = userService.createUser(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/confirmSignUp/{confirmationToken}")
    @Operation(summary = "Подтверждение регистрации пользователя")
    public ResponseEntity<AuthRsDto> confirmSignUp(@PathVariable String confirmationToken) {
        AuthRsDto result = userService.checkConfirmationSignUp(confirmationToken);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signIn")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<AuthRsDto> signIn(@RequestBody LoginUserRqDto user){
        AuthRsDto result = authService.trySignup(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/forgotPassword")
    @Operation(summary = "Запрос на восстановления пароля пользователя")
    public ResponseEntity<ConfirmationRsDto> forgotPassword(@RequestBody ConfirmationEmailRqDto confirmation){
        ConfirmationRsDto result = userService.forgotPassword(confirmation);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/confirmPasswordRecovery/{confirmationToken}")
    @Schema(description = "Подтверждение смены пароля")
    public ResponseEntity<ConfirmationRsDto> confirmPasswordRecovery(@PathVariable String confirmationToken){
        ConfirmationRsDto result = userService.confirmPasswordRecovery(confirmationToken);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeUserPassword")
    @Operation(summary = "Запрос на смену пароля")
    public ResponseEntity<AuthRsDto> changeUserPassword(@RequestBody ConfirmationEmailRqDto confirmation){
        AuthRsDto result = userService.changeUserPassword(confirmation);
        return ResponseEntity.ok(result);
    }

}
