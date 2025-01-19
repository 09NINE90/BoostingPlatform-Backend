package ru.platform.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.LocalConstants;
import ru.platform.dto.CustomUserDetails;
import ru.platform.entity.OrderServicesEntity;
import ru.platform.entity.GameEntity;
import ru.platform.entity.UserEntity;
import ru.platform.entity.enums.ESortKeys;
import ru.platform.entity.specification.OrderServicesSpecification;
import ru.platform.inner.SortFilter;
import ru.platform.repository.*;
import ru.platform.request.OrderServicesRequest;
import ru.platform.response.OrderServicesResponse;
import ru.platform.service.IMinIOFileService;
import ru.platform.service.IOrderServicesService;
import ru.platform.utils.GenerateSecondIdUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServicesService implements IOrderServicesService {

    private final OrderServicesRepository orderServicesRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GenerateSecondIdUtil generateSecondIdUtil;
    private final OrderServicesSpecification specification;
    private final IMinIOFileService minioService;

    @Override
    public OrderServicesResponse getAllServices(OrderServicesRequest request) {
        return mapToResponse(getServicePageFunc().apply(request));
    }

    @Override
    public void saveEditingService(OrderServicesEntity request) {
        OrderServicesEntity existingOrder = orderServicesRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        existingOrder.setTitle(request.getTitle());
        existingOrder.setDescription(request.getDescription());
        existingOrder.setBasePrice(request.getBasePrice());
        orderServicesRepository.save(existingOrder);
    }

    @Override
    public OrderServicesEntity addNewService(OrderServicesRequest request, MultipartFile imageFile, Authentication authentication) {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<UserEntity> user = userRepository.findById(userDetails.getId());
        Optional<GameEntity> game = gameRepository.findById(UUID.fromString(request.getGameId().replace("\"", "")));

        String imageUrl = minioService.uploadBaseOrderImage(imageFile);

        if (user.isPresent() && game.isPresent()) {
            OrderServicesEntity service = OrderServicesEntity.builder()
                    .title(request.getTitle().replace("\"", ""))
                    .creator(user.get())
                    .description(request.getDescription().replace("\"", ""))
                    .basePrice(request.getPrice())
                    .createdAt(LocalDate.now())
                    .game(game.get())
                    .categories(request.getCategories().replace("\"", ""))
                    .imageUrl(imageUrl)
                    .secondId(generateSecondIdUtil.getRandomId())
                    .build();
            orderServicesRepository.save(service);
            return service;
        }
        return null;
    }

    @Override
    public void deleteService(UUID id) {
        orderServicesRepository.deleteById(id);
    }

    private OrderServicesEntity mapServiceFrom(OrderServicesEntity e) {
        return OrderServicesEntity.builder()
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

    private OrderServicesResponse mapToResponse(Page<OrderServicesEntity> entities) {
        List<OrderServicesEntity> mappedService = entities.stream().map(this::mapServiceFrom).collect(Collectors.toList());
        return OrderServicesResponse.builder()
                .services(mappedService)
                .pageNumber(entities.getNumber() + 1)
                .pageSize(entities.getSize())
                .pageTotal(entities.getTotalPages())
                .recordTotal(entities.getTotalElements())
                .build();
    }

    private Function<OrderServicesRequest, Page<OrderServicesEntity>> getServicePageFunc() {
        return request -> orderServicesRepository.findAll(specification.getFilter(request), getPageRequest(request));
    }

    private PageRequest getPageRequest(OrderServicesRequest request) {
        return PageRequest.of(getPageBy(request), getSizeBy(request), getSortBy(request));
    }

    private Sort getSortBy(OrderServicesRequest request) {
        return getSortBy(request.getSort());
    }

    private Sort getSortBy(SortFilter sort) {
        if (sort == null || sort.getKey() == null) {
            sort = new SortFilter(ESortKeys.CREATED_AT, false);
        }
        boolean isAsc = sort.getAsc();
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sort.getKey().getName());
    }

    private int getPageBy(OrderServicesRequest request) {
        return getPageBy(request.getPageNumber());
    }

    private int getPageBy(Integer pageNumber) {
        return pageNumber == null || pageNumber <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_NUMBER : pageNumber - 1;
    }

    private int getSizeBy(OrderServicesRequest request) {
        return getSizeBy(request.getPageSize());
    }

    private int getSizeBy(Integer pageSize) {
        return pageSize == null || pageSize <= 0 ? LocalConstants.Variables.DEFAULT_PAGE_SIZE : pageSize;
    }
}
