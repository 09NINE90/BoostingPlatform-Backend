package ru.platform.games.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameBySecondIdRsDto {

    private String id;
    private String secondId;
    private String name;
    private List<CategoryRsDto> categories;
}
