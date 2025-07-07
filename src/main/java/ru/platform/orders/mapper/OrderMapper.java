package ru.platform.orders.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.platform.chat.dao.ChatRoomEntity;
import ru.platform.offers.dao.OfferCartEntity;
import ru.platform.offers.dao.OfferOptionCartEntity;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.OrderOptionEntity;
import ru.platform.orders.dto.response.*;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.user.dao.UserEntity;
import ru.platform.utils.DateTimeUtils;
import ru.platform.utils.GenerateSecondIdUtil;

import java.time.OffsetDateTime;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    /**
     * Маппинг объекта из корзины в объект сущности БД
     */
    public OrderEntity toOrderEntity(OfferCartEntity cartItemDto) {
        return OrderEntity.builder()
                .offerName(cartItemDto.getOffer().getTitle())
                .basePrice(cartItemDto.getBasePrice())
                .totalPrice(cartItemDto.getTotalPrice())
                .status(OrderStatus.CREATED)
                .game(cartItemDto.getGame())
                .gamePlatform(cartItemDto.getGamePlatform())
                .totalTime(cartItemDto.getTotalTime())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .optionList(toOrderOptionList(cartItemDto.getOptionCarts()))
                .build();
    }

    /**
     * Маппинг списка опций объекта из корзины в список опций объекта сущности БД
     */
    private List<OrderOptionEntity> toOrderOptionList(List<OfferOptionCartEntity> cartSelectedOptionsList) {
        if (cartSelectedOptionsList == null || cartSelectedOptionsList.isEmpty()) return emptyList();
        return cartSelectedOptionsList.stream().map(this::toOrderOption).toList();
    }

    /**
     * Маппинг объекта опции корзины в объект опции сущности БД
     */
    private OrderOptionEntity toOrderOption(OfferOptionCartEntity cartSelectedOptionsDto) {
        return OrderOptionEntity.builder()
                .value(cartSelectedOptionsDto.getValue())
                .label(cartSelectedOptionsDto.getLabel())
                .optionTitle(cartSelectedOptionsDto.getOptionTitle())
                .build();
    }

    public OrderFromCartRsDto toOrderFromCartDto(OrderEntity orderEntity) {
        return OrderFromCartRsDto.builder()
                .orderName(orderEntity.getOfferName())
                .orderStatus(orderEntity.getStatus())
                .gameName(orderEntity.getGame().getTitle())
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
        UserEntity booster = orderEntity.getBooster();
        ChatRoomEntity chatRoom = orderEntity.getChatRoom();
        return OrderRsDto.builder()
                .chatId(chatRoom != null ? chatRoom.getId().toString() : null)
                .boosterId(booster != null ? booster.getId().toString() : null)
                .orderId(orderEntity.getId().toString())
                .secondId(GenerateSecondIdUtil.toRandomLookingId(orderEntity.getSecondId()))
                .offerName(orderEntity.getOfferName())
                .gameName(orderEntity.getGame().getTitle())
                .gamePlatform(orderEntity.getGamePlatform())
                .orderStatus(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .selectedOptions(toOrderListOptionDtoList(orderEntity.getOptionList()))
                .startTimeExecution(DateTimeUtils.offsetDateTimeToStringUTC(orderEntity.getStartTimeExecution()))
                .endTimeExecution(DateTimeUtils.offsetDateTimeToStringUTC(orderEntity.getEndTimeExecution()))
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
                .chatId(orderEntity.getChatRoom() != null ? orderEntity.getChatRoom().getId().toString() : null)
                .orderId(orderEntity.getId().toString())
                .secondId(GenerateSecondIdUtil.toRandomLookingId(orderEntity.getSecondId()))
                .offerName(orderEntity.getOfferName())
                .gameName(orderEntity.getGame().getTitle())
                .gamePlatform(orderEntity.getGamePlatform())
                .orderStatus(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice().doubleValue())
                .boosterSalary(orderEntity.getBoosterSalary().doubleValue())
                .selectedOptions(toOrderByBoosterListOptionDtoList(orderEntity.getOptionList()))
                .startTimeExecution(DateTimeUtils.offsetDateTimeToStringUTC(orderEntity.getStartTimeExecution()))
                .endTimeExecution(DateTimeUtils.offsetDateTimeToStringUTC(orderEntity.getEndTimeExecution()))
                .completedAt(DateTimeUtils.offsetDateTimeToStringUTC(orderEntity.getCompletedAt()))
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

    public BoosterOrderHistoryRsDto toBoosterOrderHistoryRsDto(OrderEntity orderEntity) {
        return BoosterOrderHistoryRsDto.builder()
                .id(orderEntity.getId().toString())
                .orderId(GenerateSecondIdUtil.toRandomLookingId(orderEntity.getSecondId()))
                .completedAt(DateTimeUtils.offsetDateTimeToStringUTC(orderEntity.getCompletedAt()))
                .salary(orderEntity.getBoosterSalary())
                .orderName(orderEntity.getOfferName())
                .orderStatus(orderEntity.getStatus())
                .gameName(orderEntity.getGame().getTitle())
                .build();
    }
}
