package ru.platform.games.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMainPageRsDto {
    private String gameId;
    private String gameTitle;
}
