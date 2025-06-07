package ru.platform.orders.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderListRsDto {

    @ArraySchema(schema = @Schema(description = "Список заказов для дашборда бустера"))
    List<OrderRsDto> orders;

    @Schema(description = "Всего страниц", example = "5")
    private int pageTotal;

    @Schema(description = "Номер страницы", example = "1")
    private int pageNumber;

    @Schema(description = "Количество объектов на одной странице", example = "20")
    private int pageSize;

    @Schema(description = "Всего объектов найдено", example = "100")
    private long recordTotal;

}
