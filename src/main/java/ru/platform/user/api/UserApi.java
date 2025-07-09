package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.response.BoosterProfileRsDto;
import ru.platform.user.dto.response.CustomerProfileRsDto;
import ru.platform.user.dto.response.MiniBoosterProfileRsDto;
import ru.platform.user.service.IUserService;

import java.util.UUID;

import static ru.platform.LocalConstants.Api.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = USER_TAG_NAME, description = USER_TAG_DESCRIPTION)
public class UserApi {

    private final IUserService userService;

    @GetMapping("/getCustomerProfileData")
    @Operation(summary = "Получить профиль заказчика")
    public ResponseEntity<CustomerProfileRsDto> getUserProfileData() {
        CustomerProfileRsDto result = userService.getCustomerProfileData();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getBoosterProfileData")
    @Operation(summary = "Получить профиль бустера")
    public ResponseEntity<BoosterProfileRsDto> getBoosterProfileData() {
        BoosterProfileRsDto result = userService.getBoosterProfileData();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeNickname")
    @Operation(summary = "Изменить никнейм")
    public ResponseEntity<Void> changeNickname(@RequestParam String nickname) {
        userService.changeNickname(nickname);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeDescriptionProfile")
    @Operation(summary = "Изменить описание профиля")
    public ResponseEntity<Void> changeDescriptionProfile(@RequestParam String description) {
        userService.changeDescription(description);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getBoosterMiniProfile/{boosterId}")
    @Operation(summary = "Получить краткий профиль бустера")
    public ResponseEntity<MiniBoosterProfileRsDto> getBoosterMiniProfile(@PathVariable("boosterId") UUID boosterId) {
        return ResponseEntity.ok(userService.getBoosterMiniProfile(boosterId));
    }
}
