package ru.platform.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.platform.LocalConstants;
import ru.platform.dto.CustomUserDetails;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.entity.GameEntity;
import ru.platform.entity.UserEntity;
import ru.platform.entity.specification.BaseOrderSpecification;
import ru.platform.repository.*;
import ru.platform.request.BaseOrderEditRequest;
import ru.platform.response.BaseOrderResponse;
import ru.platform.service.IOrdersService;
import ru.platform.utils.GenerateSecondIdUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService implements IOrdersService {

    private final OrdersByCustomersRepository ordersByCustomersRepository;
    private final OrdersPerWeekRepository ordersPerWeekRepository;
    private final BaseOrdersRepository baseOrdersRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GenerateSecondIdUtil generateSecondIdUtil;
    private final BaseOrderSpecification specification;

    @Override
    public BaseOrderResponse getAllOrders(BaseOrderEditRequest request) {
        return mapToResponse(getBaseOrderPageFunc().apply(request));
    }

    @Override
    public void saveEditingBaseOrder(BaseOrdersEntity request) {
        BaseOrdersEntity existingOrder = baseOrdersRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        existingOrder.setTitle(request.getTitle());
        existingOrder.setDescription(request.getDescription());
        existingOrder.setBasePrice(request.getBasePrice());
        baseOrdersRepository.save(existingOrder);
    }

    @Override
    public BaseOrdersEntity addNewBaseOrder(BaseOrderEditRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<UserEntity> user = userRepository.findById(userDetails.getId());
        Optional<GameEntity> game = gameRepository.findById(request.getGame().getId());

        if (user.isPresent() && game.isPresent()){
            return(baseOrdersRepository.save(BaseOrdersEntity.builder()
                                .title(request.getTitle())
                                .creator(user.get())
                                .description(request.getDescription())
                                .basePrice(request.getBasePrice())
                                .createdAt(LocalDate.now())
                                .game(game.get())
                                .secondId(generateSecondIdUtil.getRandomId())
                                .build()));
        }
        return null;
    }

    @Override
    public void deleteBaseOrder(UUID id) {
        baseOrdersRepository.deleteById(id);
    }

    private BaseOrdersEntity mapBaseOrderFrom(BaseOrdersEntity e){
        return BaseOrdersEntity.builder()
                .id(e.getId())
                .basePrice(e.getBasePrice())
                .title(e.getTitle())
                .game(e.getGame())
                .createdAt(e.getCreatedAt())
                .description(e.getDescription())
                .secondId(e.getSecondId())
                .build();
    }

    private BaseOrderResponse mapToResponse(Page<BaseOrdersEntity> entities){
        List<BaseOrdersEntity> mappedOrders = entities.stream().map(this::mapBaseOrderFrom).collect(Collectors.toList());
        return BaseOrderResponse.builder()
                .baseOrder(mappedOrders)
                .pageNumber(entities.getNumber() + 1)
                .pageSize(entities.getSize())
                .pageTotal(entities.getTotalPages())
                .recordTotal(entities.getTotalElements())
                .build();
    }
    private Function<BaseOrderEditRequest, Page<BaseOrdersEntity>> getBaseOrderPageFunc(){
        return request -> baseOrdersRepository.findAll(specification.getFilter(request), getPageRequest(request));
    }

    private PageRequest getPageRequest(BaseOrderEditRequest request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request));
    }

    private int getPageBy(BaseOrderEditRequest request) {
        return getPageBy(request.getPageNumber());
    }

    private int getPageBy(Integer pageNumber) {
        return pageNumber == null || pageNumber <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_NUMBER : pageNumber - 1;
    }

    private int getSizeBy(BaseOrderEditRequest request) {
        return getSizeBy(request.getPageSize());
    }

    private int getSizeBy(Integer pageSize) {
        return pageSize == null || pageSize <=0 ? LocalConstants.Variables.DEFAULT_PAGE_SIZE : pageSize;
    }
}
