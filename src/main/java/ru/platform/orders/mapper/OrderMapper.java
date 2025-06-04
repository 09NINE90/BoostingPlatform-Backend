package ru.platform.orders.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderOptionEntity;
import ru.platform.orders.dto.request.CartItemDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.user.service.IAuthService;

import java.util.List;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final IAuthService authService;

    public OrderEntity toOrder(CartItemDto cartItemDto) {
        return OrderEntity.builder()
                .creator(authService.getAuthUser())
                .offerName(cartItemDto.getOfferName())
                .basePrice(cartItemDto.getBasePrice())
                .totalPrice(cartItemDto.getTotalPrice())
                .status(OrderStatus.CREATED.name())
                .gameName(cartItemDto.getGameName())
                .totalTime(cartItemDto.getTotalTime())
                .optionList(toOrderOptionList(cartItemDto.getSelectedOptions()))
                .build();
    }

    private List<OrderOptionEntity> toOrderOptionList(List<CartItemDto.CartSelectedOptionsDto> cartSelectedOptionsList) {
        if (cartSelectedOptionsList == null || cartSelectedOptionsList.isEmpty()) return emptyList();
        return cartSelectedOptionsList.stream().map(this::toOrderOption).toList();
    }

    private OrderOptionEntity toOrderOption(CartItemDto.CartSelectedOptionsDto cartSelectedOptionsDto) {
        return OrderOptionEntity.builder()
                .value(cartSelectedOptionsDto.getValue().toString())
                .label(cartSelectedOptionsDto.getLabel().toString())
                .optionTitle(cartSelectedOptionsDto.getOptionTitle())
                .build();
    }

    public OrderFromCartRsDto toOrderFromCartDto(OrderEntity orderEntity) {
        return OrderFromCartRsDto.builder()
                .orderName(orderEntity.getOfferName())
                .orderStatus(OrderStatus.valueOf(orderEntity.getStatus()))
                .gameName(orderEntity.getGameName())
                .totalPrice(orderEntity.getTotalPrice())
                .totalTime(orderEntity.getTotalTime())
                .selectedOptions(toOrderOptionDtoList(orderEntity.getOptionList()))
                .build();
    }

    private List<OrderFromCartRsDto.CartSelectedOptionsDto> toOrderOptionDtoList(List<OrderOptionEntity> orderOptionEntities) {
        if (orderOptionEntities == null || orderOptionEntities.isEmpty()) return emptyList();
        return orderOptionEntities.stream().map(this::toOrderOptionDto).toList();
    }

    private OrderFromCartRsDto.CartSelectedOptionsDto toOrderOptionDto(OrderOptionEntity cartSelectedOptionsDto) {
        return OrderFromCartRsDto.CartSelectedOptionsDto.builder()
                .value(cartSelectedOptionsDto.getValue())
                .label(cartSelectedOptionsDto.getLabel())
                .optionTitle(cartSelectedOptionsDto.getOptionTitle())
                .build();
    }

    public OrderListRsDto toOrderLisRsDto(OrderEntity orderEntity) {
        return OrderListRsDto.builder()
                .orderId(String.valueOf(orderEntity.getSecondId()))
                .offerName(orderEntity.getOfferName())
                .gameName(orderEntity.getGameName())
                .orderStatus(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .build();
    }
}
