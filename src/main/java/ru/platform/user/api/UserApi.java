package ru.platform.user.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.user.dto.response.UserProfileRsDto;
import ru.platform.user.service.IUserService;

import static ru.platform.LocalConstants.Api.AUTH_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.AUTH_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = AUTH_TAG_NAME, description = AUTH_TAG_DESCRIPTION)
public class UserApi {

    private final IUserService userService;

    @GetMapping("/getUserProfileData")
    public ResponseEntity<UserProfileRsDto> getUserProfileData() {
        UserProfileRsDto result = userService.getUserProfileData();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeNickname")
    public ResponseEntity<?> changeNickname(@RequestParam String nickname) {
        String result = userService.changeNickname(nickname);
        return ResponseEntity.ok(result);
    }
}
