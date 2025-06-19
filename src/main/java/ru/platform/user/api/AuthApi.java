package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.request.ConfirmationEmailRqDto;
import ru.platform.user.dto.request.EmailConfirmationRequest;
import ru.platform.user.dto.request.SignupUserRqDto;
import ru.platform.user.dto.request.LoginUserRqDto;
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
        ConfirmationRsDto result = userService.registrationUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirmSignUp")
    @Operation(summary = "Подтверждение регистрации пользователя")
    public ResponseEntity<?> confirmSignUp(@RequestBody EmailConfirmationRequest confirmationToken, HttpServletResponse response) {
        return ResponseEntity.ok(userService.checkConfirmationSignUp(confirmationToken.getToken(), response));
    }

    @PostMapping("/resendConfirmationEmail")
    @Operation(summary = "Повторная отправка токена пользователя для подтверждения регистрации")
    public ResponseEntity<ConfirmationRsDto> resendConfirmationEmail(@RequestBody ConfirmationEmailRqDto confirmation) {
        ConfirmationRsDto result = userService.resendConfirmationEmail(confirmation);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signIn")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<?> signIn(@RequestBody LoginUserRqDto user, HttpServletResponse response) {
        return ResponseEntity.ok(authService.trySignIn(user, response));
    }

    @PostMapping("/logout")
    @Operation(summary = "Запрос на выход пользователя из системы")
    public ResponseEntity<Void> logout( HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновление access токена по refresh токену из куки")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.refreshAccessToken(request, response));
    }

    @PostMapping("/forgotPassword")
    @Operation(summary = "Запрос на восстановления пароля пользователя")
    public ResponseEntity<ConfirmationRsDto> forgotPassword(@RequestBody ConfirmationEmailRqDto confirmation) {
        ConfirmationRsDto result = userService.forgotPassword(confirmation);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/confirmPasswordRecovery/{confirmationToken}")
    @Schema(description = "Подтверждение смены пароля")
    public ResponseEntity<ConfirmationRsDto> confirmPasswordRecovery(@PathVariable String confirmationToken) {
        ConfirmationRsDto result = userService.confirmPasswordRecovery(confirmationToken);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeUserPassword")
    @Operation(summary = "Запрос на смену пароля")
    public ResponseEntity<?> changeUserPassword(@RequestBody ConfirmationEmailRqDto confirmation, HttpServletResponse response) {
        return ResponseEntity.ok(userService.changeUserPassword(confirmation, response));
    }

}
