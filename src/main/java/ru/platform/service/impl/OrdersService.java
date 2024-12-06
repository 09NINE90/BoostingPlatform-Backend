package ru.platform.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.LocalConstants;
import ru.platform.dto.CustomUserDetails;
import ru.platform.entity.BaseOrdersEntity;
import ru.platform.entity.GameEntity;
import ru.platform.entity.UserEntity;
import ru.platform.entity.specification.BaseOrderSpecification;
import ru.platform.repository.*;
import ru.platform.request.BaseOrderRequest;
import ru.platform.response.BaseOrderResponse;
import ru.platform.service.IMinIOFileService;
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
    private final IMinIOFileService minioService;

    @Override
    public BaseOrderResponse getAllOrders(BaseOrderRequest request) {
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
    public BaseOrdersEntity addNewBaseOrder(String title, String description, String price, String selectedGameId, String categories, MultipartFile imageFile, Authentication authentication) {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<UserEntity> user = userRepository.findById(userDetails.getId());
        Optional<GameEntity> game = gameRepository.findById(UUID.fromString(selectedGameId.replace("\"", "")));

        String imageUrl = minioService.uploadBaseOrderImage(imageFile);

        if (user.isPresent() && game.isPresent()){
            return(baseOrdersRepository.save(BaseOrdersEntity.builder()
                                .title(title.replace("\"", ""))
                                .creator(user.get())
                                .description(description.replace("\"", ""))
                                .basePrice(Float.parseFloat(price.replace("\"", "")))
                                .createdAt(LocalDate.now())
                                .game(game.get())
                                .categories(categories.replace("\"", ""))
                                .imageUrl(imageUrl)
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
                .imageUrl(e.getImageUrl())
                .categories(e.getCategories())
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
    private Function<BaseOrderRequest, Page<BaseOrdersEntity>> getBaseOrderPageFunc(){
        return request -> baseOrdersRepository.findAll(specification.getFilter(request), getPageRequest(request));
    }

    private PageRequest getPageRequest(BaseOrderRequest request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request));
    }

    private int getPageBy(BaseOrderRequest request) {
        return getPageBy(request.getPageNumber());
    }

    private int getPageBy(Integer pageNumber) {
        return pageNumber == null || pageNumber <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_NUMBER : pageNumber - 1;
    }

    private int getSizeBy(BaseOrderRequest request) {
        return getSizeBy(request.getPageSize());
    }

    private int getSizeBy(Integer pageSize) {
        return pageSize == null || pageSize <=0 ? LocalConstants.Variables.DEFAULT_PAGE_SIZE : pageSize;
    }
}
