package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.response.UserProfileRsDto;
import ru.platform.user.service.IUserService;

import static ru.platform.LocalConstants.Api.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = USER_TAG_NAME, description = USER_TAG_DESCRIPTION)
public class UserApi {

    private final IUserService userService;

    @GetMapping("/getUserProfileData")
    @Operation(summary = "Запрос на получение данных профиля пользователя")
    public ResponseEntity<UserProfileRsDto> getUserProfileData() {
        UserProfileRsDto result = userService.getUserProfileData();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeNickname")
    @Operation(summary = "Запрос на смену никнейма пользователя")
    public ResponseEntity<?> changeNickname(@RequestParam String nickname) {
        String result = userService.changeNickname(nickname);
        return ResponseEntity.ok(result);
    }
}
