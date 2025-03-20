package ru.platform.games.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameListRsDto<T> {

    @ArraySchema(schema = @Schema(description = "Список игр для ответа фронту"))
    private List<T> games;

}
