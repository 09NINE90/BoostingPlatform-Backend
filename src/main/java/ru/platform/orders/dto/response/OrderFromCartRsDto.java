package ru.platform.orders.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.platform.orders.dto.request.CartSelectedOptionsDto;
import ru.platform.orders.enumz.OrderStatus;

import java.util.List;

@Data
@Builder
public class OrderFromCartRsDto {

    private String gameName;
    private OrderStatus orderStatus;
    private List<CartSelectedOptionsDto> selectedOptions;
    private double totalPrice;
    private int totalTime;

}
