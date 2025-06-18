package ru.platform.orders.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderOptionEntity;
import ru.platform.orders.dto.request.CartItemDto;
import ru.platform.orders.dto.request.OrdersByBoosterRqDto;
import ru.platform.orders.dto.request.OrdersByFiltersRqDto;
import ru.platform.orders.dto.response.OrderByBoosterRsDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderListRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.user.service.IAuthService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final IAuthService authService;

    /**
     * Маппинг объекта из корзины в объект сущности БД
     */
    public OrderEntity toOrderEntity(CartItemDto cartItemDto) {
        return OrderEntity.builder()
                .creator(authService.getAuthUser())
                .offerName(cartItemDto.getOfferName())
                .basePrice(BigDecimal.valueOf(cartItemDto.getBasePrice()))
                .totalPrice(BigDecimal.valueOf(cartItemDto.getTotalPrice()))
                .status(OrderStatus.CREATED.name())
                .gameName(cartItemDto.getGameName())
                .gamePlatform(cartItemDto.getGamePlatform())
                .totalTime(cartItemDto.getTotalTime())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .optionList(toOrderOptionList(cartItemDto.getSelectedOptions()))
                .build();
    }

    /**
     * Маппинг списка опций объекта из корзины в список опций объекта сущности БД
     */
    private List<OrderOptionEntity> toOrderOptionList(List<CartItemDto.CartSelectedOptionsDto> cartSelectedOptionsList) {
        if (cartSelectedOptionsList == null || cartSelectedOptionsList.isEmpty()) return emptyList();
        return cartSelectedOptionsList.stream().map(this::toOrderOption).toList();
    }

    /**
     * Маппинг объекта опции корзины в объект опции сущности БД
     */
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
                .totalPrice(orderEntity.getTotalPrice().doubleValue())
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

    public OrderListRsDto toOrderListRsDto(Page<OrderEntity> orderEntity) {
        List<OrderRsDto> orders = orderEntity.map(this::toOrderRsDto).toList();
        return OrderListRsDto.builder()
                .orders(orders)
                .pageNumber(orderEntity.getNumber())
                .pageSize(orderEntity.getSize())
                .pageTotal(orderEntity.getTotalPages())
                .recordTotal(orderEntity.getTotalElements())
                .build();
    }

    public OrderRsDto toOrderRsDto(OrderEntity orderEntity) {
        return OrderRsDto.builder()
                .orderId(orderEntity.getId().toString())
                .secondId(String.valueOf(orderEntity.getSecondId()))
                .offerName(orderEntity.getOfferName())
                .gameName(orderEntity.getGameName())
                .gamePlatform(orderEntity.getGamePlatform())
                .orderStatus(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice().doubleValue())
                .selectedOptions(toOrderListOptionDtoList(orderEntity.getOptionList()))
                .build();
    }

    private List<OrderRsDto.CartSelectedOptionsDto> toOrderListOptionDtoList(List<OrderOptionEntity> orderOptionEntities) {
        if (orderOptionEntities == null || orderOptionEntities.isEmpty()) return emptyList();
        return orderOptionEntities.stream().map(this::toOrderListOptionDto).toList();
    }

    private OrderRsDto.CartSelectedOptionsDto toOrderListOptionDto(OrderOptionEntity cartSelectedOptionsDto) {
        return OrderRsDto.CartSelectedOptionsDto.builder()
                .value(cartSelectedOptionsDto.getValue())
                .label(cartSelectedOptionsDto.getLabel())
                .optionTitle(cartSelectedOptionsDto.getOptionTitle())
                .build();
    }

    public OrderByBoosterRsDto toOrderByBoosterRsDto(OrderEntity orderEntity) {
        return OrderByBoosterRsDto.builder()
                .orderId(orderEntity.getId().toString())
                .secondId(String.valueOf(orderEntity.getSecondId()))
                .offerName(orderEntity.getOfferName())
                .gameName(orderEntity.getGameName())
                .gamePlatform(orderEntity.getGamePlatform())
                .orderStatus(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice().doubleValue())
                .boosterSalary(orderEntity.getBoosterSalary().doubleValue())
                .selectedOptions(toOrderByBoosterListOptionDtoList(orderEntity.getOptionList()))
                .build();
    }

    private List<OrderByBoosterRsDto.CartSelectedOptionsDto> toOrderByBoosterListOptionDtoList(List<OrderOptionEntity> orderOptionEntities) {
        if (orderOptionEntities == null || orderOptionEntities.isEmpty()) return emptyList();
        return orderOptionEntities.stream().map(this::toOrderByBoosterListOptionDto).toList();
    }

    private OrderByBoosterRsDto.CartSelectedOptionsDto toOrderByBoosterListOptionDto(OrderOptionEntity cartSelectedOptionsDto) {
        return OrderByBoosterRsDto.CartSelectedOptionsDto.builder()
                .value(cartSelectedOptionsDto.getValue())
                .label(cartSelectedOptionsDto.getLabel())
                .optionTitle(cartSelectedOptionsDto.getOptionTitle())
                .build();
    }

    /**
     * Маппинг объекта запроса на получения ордеров закрепленных за бустером
     * в объект запроса для получения отфильтрованных и отсортированных ордеров
     */
    public OrdersByFiltersRqDto toOrdersByFiltersRqDto(OrdersByBoosterRqDto request) {
        return OrdersByFiltersRqDto.builder()
                .booster(request.getBooster())
                .status(request.getStatus())
                .gameName(request.getGameName())
                .gamePlatform(request.getGamePlatform())
                .boosterPrice(OrdersByFiltersRqDto.PriceDto.builder()
                        .priceFrom(request.getPrice().getPriceFrom())
                        .priceTo(request.getPrice().getPriceTo())
                        .build()
                )
                .sort(request.getSort())
                .build();
    }
}
