package ru.platform.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IValidationOrderService;
import ru.platform.orders.service.impl.OrderCustomerService;
import ru.platform.service.CreatorDto;
import ru.platform.user.service.IAuthService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderCustomerServiceTest {

    private OrderMapper mapper;
    private IAuthService authService;
    private OrderRepository orderRepository;
    private OrderCustomerService orderCustomerService;
    private IValidationOrderService  validationOrderService;

    @BeforeEach
    public void setUp() {
        mapper = mock(OrderMapper.class);
        authService = mock(IAuthService.class);
        orderRepository = mock(OrderRepository.class);
        validationOrderService = mock(IValidationOrderService.class);
        orderCustomerService = new OrderCustomerService(
                mapper, authService, orderRepository, validationOrderService
        );
    }

    @Test
    @DisplayName("Заказы: получение списка заказов пользователя - успех")
    void getOrdersByCreatorSuccess() {
        OrderRsDto expectedDto = CreatorDto.getOrderRsDto();

        when(authService.getAuthUser()).thenReturn(CreatorDto.getCustomerUserEntity());
        when(orderRepository.findAllByStatusAndByCreator(any(), any())).thenReturn(CreatorDto.getListOfOrdersEntity());
        when(mapper.toOrderRsDto(any())).thenReturn(expectedDto);

        List<OrderRsDto> response = orderCustomerService.getOrdersByCreator(OrderStatus.IN_PROGRESS);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size()),
                () -> {
                    OrderRsDto actualDto = response.getFirst();
                    assertAll(
                            () -> assertEquals(expectedDto.getOrderId(), actualDto.getOrderId(), "Order ID mismatch"),
                            () -> assertEquals(expectedDto.getSecondId(), actualDto.getSecondId(), "Second ID mismatch"),
                            () -> assertEquals(expectedDto.getOfferName(), actualDto.getOfferName(), "Offer name mismatch"),
                            () -> assertEquals(expectedDto.getGameName(), actualDto.getGameName(), "Game name mismatch"),
                            () -> assertEquals(expectedDto.getGamePlatform(), actualDto.getGamePlatform(), "Game platform mismatch"),
                            () -> assertEquals(expectedDto.getOrderStatus(), actualDto.getOrderStatus(), "Order status mismatch"),
                            () -> assertEquals(expectedDto.getTotalPrice(), actualDto.getTotalPrice()),
                            () -> assertNotNull(actualDto.getSelectedOptions(), "Selected options should not be null"),
                            () -> assertEquals(1, actualDto.getSelectedOptions().size(), "Should have one selected option")
                    );
                }
        );
    }

    @Test
    @DisplayName("Заказы: получение списка заказов пользователя - ошибка авторизации")
    void getOrdersByCreatorErrorAuth() {
        when(authService.getAuthUser()).thenThrow(new PlatformException(ErrorType.AUTHORIZATION_ERROR));

        PlatformException exception = assertThrows(PlatformException.class, () -> orderCustomerService.getOrdersByCreator(OrderStatus.IN_PROGRESS));

        assertEquals(ErrorType.AUTHORIZATION_ERROR, exception.getErrorType());
    }
}
