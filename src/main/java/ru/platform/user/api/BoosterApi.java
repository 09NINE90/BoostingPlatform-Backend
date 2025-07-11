package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.user.dto.response.BoosterProfileRsDto;
import ru.platform.user.dto.response.MiniBoosterProfileRsDto;
import ru.platform.user.service.IBoosterService;

import java.util.UUID;

import static ru.platform.LocalConstants.Api.BOOSTER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.BOOSTER_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boosters")
@Tag(name = BOOSTER_TAG_NAME, description = BOOSTER_TAG_DESCRIPTION)
public class BoosterApi {

    private final IBoosterService boosterService;

    @GetMapping("/me/profile")
    @Operation(summary = "Получить профиль бустера")
    public ResponseEntity<BoosterProfileRsDto> getBoosterProfileData() {
        BoosterProfileRsDto result = boosterService.getBoosterProfileData();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{boosterId}/mini-profile")
    @Operation(summary = "Получить краткий профиль бустера")
    public ResponseEntity<MiniBoosterProfileRsDto> getBoosterMiniProfile(@PathVariable("boosterId") UUID boosterId) {
        return ResponseEntity.ok(boosterService.getBoosterMiniProfile(boosterId));
    }
}
