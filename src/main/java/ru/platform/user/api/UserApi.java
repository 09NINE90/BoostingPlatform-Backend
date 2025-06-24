package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.response.BoosterProfileRsDto;
import ru.platform.user.dto.response.CustomerProfileRsDto;
import ru.platform.user.service.IUserService;

import static ru.platform.LocalConstants.Api.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = USER_TAG_NAME, description = USER_TAG_DESCRIPTION)
public class UserApi {

    private final IUserService userService;

    @GetMapping("/getCustomerProfileData")
    @Operation(summary = "Запрос на получение данных профиля заказчика")
    public ResponseEntity<CustomerProfileRsDto> getUserProfileData() {
        CustomerProfileRsDto result = userService.getCustomerProfileData();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getBoosterProfileData")
    @Operation(summary = "Запрос на получение данных профиля бустера")
    public ResponseEntity<BoosterProfileRsDto> getBoosterProfileData() {
        BoosterProfileRsDto result = userService.getBoosterProfileData();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeNickname")
    @Operation(summary = "Запрос на смену никнейма пользователя")
    public ResponseEntity<Void> changeNickname(@RequestParam String nickname) {
        userService.changeNickname(nickname);
        return ResponseEntity.ok().build();
    }
}
