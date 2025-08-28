package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.service.IUserService;

import static ru.platform.LocalConstants.Api.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = USER_TAG_NAME, description = USER_TAG_DESCRIPTION)
public class UserApi {

    private final IUserService userService;

    @PostMapping("/me/nickname")
    @Operation(summary = "Изменить никнейм")
    public ResponseEntity<Void> changeNickname(@RequestParam String nickname) {
        userService.changeNickname(nickname);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me/description")
    @Operation(summary = "Изменить описание профиля")
    public ResponseEntity<Void> changeDescriptionProfile(@RequestParam String description) {
        userService.changeDescription(description);
        return ResponseEntity.ok().build();
    }
}
