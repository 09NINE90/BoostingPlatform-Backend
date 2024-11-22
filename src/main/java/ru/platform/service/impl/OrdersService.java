package ru.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.repository.BaseOrdersRepository;
import ru.platform.repository.OrdersByCustomersRepository;
import ru.platform.repository.OrdersPerWeekRepository;
import ru.platform.request.BaseOrderRequest;
import ru.platform.response.BaseOrderResponse;
import ru.platform.service.IOrdersService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService implements IOrdersService {

    private final OrdersByCustomersRepository ordersByCustomersRepository;
    private final OrdersPerWeekRepository ordersPerWeekRepository;
    private final BaseOrdersRepository baseOrdersRepository;

    @Override
    public BaseOrderResponse getAllOrders(BaseOrderRequest request) {
        return mapToResponse(getBaseOrderPageFunc().apply(request));
    }

    private BaseOrdersEntity mapBaseOrderFrom(BaseOrdersEntity e){
        return BaseOrdersEntity.builder()
                .basePrice(e.getBasePrice())
                .title(e.getTitle())
                .createdAt(e.getCreatedAt())
                .description(e.getDescription())
                .secondId(e.getSecondId())
                .build();
    }

    private BaseOrderResponse mapToResponse(Page<BaseOrdersEntity> entities){
        List<BaseOrdersEntity> mappedOrders = entities.stream().map(this::mapBaseOrderFrom).collect(Collectors.toList());
        return BaseOrderResponse.builder()
                .baseOrder(mappedOrders)
                .pageNumber(1)
                .pageSize(20)
                .build();
    }
    private Function<BaseOrderRequest, Page<BaseOrdersEntity>> getBaseOrderPageFunc(){
        return request -> baseOrdersRepository.findAll(getPageRequest(request));
    }

    private PageRequest getPageRequest(BaseOrderRequest request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request));
    }

    private int getPageBy(BaseOrderRequest request) {
        return getPageBy(request.getPageNumber());
    }

    private int getPageBy(Integer pageNumber) {
        return pageNumber == null || pageNumber <= 0 ? 0 : pageNumber - 1;
    }

    private int getSizeBy(BaseOrderRequest request) {
        return 20;
    }

    private int getSizeBy(Integer pageSize) {
        return pageSize == null ? 20 : pageSize;
    }
}
