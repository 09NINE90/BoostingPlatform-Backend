package ru.platform.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dao.repository.OrderRepository;
import ru.platform.orders.dao.specification.OrderSpecification;
import ru.platform.orders.dto.request.CartItemDto;
import ru.platform.orders.dto.request.CreateOrderRqDto;
import ru.platform.orders.dto.response.OrderFromCartRsDto;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.orders.enumz.OrderStatus;
import ru.platform.orders.mapper.OrderMapper;
import ru.platform.orders.service.IValidationOrderService;
import ru.platform.orders.service.impl.OrderCustomerService;
import ru.platform.service.CreatorDto;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderCustomerServiceTest {

    private OrderMapper mapper;
    private IAuthService authService;
    private OrderRepository orderRepository;
    private OrderSpecification specification;
    private OrderCustomerService orderCustomerService;
    private IValidationOrderService  validationOrderService;

    @BeforeEach
    public void setUp() {
        mapper = mock(OrderMapper.class);
        authService = mock(IAuthService.class);
        orderRepository = mock(OrderRepository.class);
        specification = mock(OrderSpecification.class);
        validationOrderService = mock(IValidationOrderService.class);
        orderCustomerService = new OrderCustomerService(
                mapper, authService, orderRepository,
                specification, validationOrderService
        );
    }

    @Test
    @DisplayName("Заказы: получение списка заказов пользователя - успех")
    void getOrdersByCreatorSuccess() {
        OrderRsDto expectedDto = CreatorDto.getOrderRsDto();

        when(authService.getAuthUser()).thenReturn(CreatorDto.getCustomerUserEntity());
        when(orderRepository.findAll(specification.getFilter(any()))).thenReturn(CreatorDto.getListOfOrdersEntity());
        when(mapper.toOrderRsDto(any())).thenReturn(expectedDto);

        List<OrderRsDto> response = orderCustomerService.getByCreator(OrderStatus.IN_PROGRESS);

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
                            () -> assertEquals(expectedDto.getTotalPrice(), actualDto.getTotalPrice(), 0.001, "Total price mismatch"),
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

        PlatformException exception = assertThrows(PlatformException.class, () -> orderCustomerService.getByCreator(OrderStatus.IN_PROGRESS));

        assertEquals(ErrorType.AUTHORIZATION_ERROR, exception.getErrorType());
    }

    @Test
    @DisplayName("Заказы: создание заказа пользователем - успех")
    void createOrderSuccess() {
        CreateOrderRqDto requestDto = CreatorDto.getCreateOrderRqDto();
        OrderFromCartRsDto expectedDto = CreatorDto.getOrderFromCartRsDto();
        UserEntity user = CreatorDto.getCustomerUserEntity();
        OrderEntity savedOrder = CreatorDto.getOrderEntity();
        List<OrderEntity> userOrders = List.of(savedOrder);

        doNothing().when(validationOrderService).validateCreateOrderRqDto(any());
        when(authService.getAuthUser()).thenReturn(user);
        when(mapper.toOrderEntity(any(CartItemDto.class))).thenReturn(savedOrder);
        when(orderRepository.saveAll(anyList())).thenReturn(List.of(savedOrder));
        when(orderRepository.findAllByCreator(user)).thenReturn(userOrders);
        when(mapper.toOrderFromCartDto(savedOrder)).thenReturn(expectedDto);

        List<OrderFromCartRsDto> response = orderCustomerService.createOrder(requestDto);

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(1, response.size(), "Should return exactly one order"),
                () -> {
                    OrderFromCartRsDto actualDto = response.getFirst();
                    assertAll(
                            () -> assertEquals(expectedDto.getOrderName(), actualDto.getOrderName(), "Order name mismatch"),
                            () -> assertEquals(expectedDto.getGameName(), actualDto.getGameName(), "Game name mismatch"),
                            () -> assertEquals(expectedDto.getOrderStatus(), actualDto.getOrderStatus(), "Order status mismatch"),
                            () -> assertEquals(expectedDto.getTotalPrice(), actualDto.getTotalPrice(), 0.001, "Total price mismatch"),
                            () -> assertEquals(expectedDto.getTotalTime(), actualDto.getTotalTime(), "Total time mismatch"),
                            () -> assertNotNull(actualDto.getSelectedOptions(), "Selected options should not be null"),
                            () -> assertEquals(expectedDto.getSelectedOptions().size(), actualDto.getSelectedOptions().size(),
                                    "Selected options count mismatch")
                    );
                }
        );

        verify(orderRepository).findAllByCreator(user);
        verify(mapper, times(requestDto.getItems().size())).toOrderEntity(any(CartItemDto.class));
    }

}
