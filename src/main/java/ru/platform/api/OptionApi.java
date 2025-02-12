package ru.platform.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.platform.entity.OrderServicesEntity;
import ru.platform.entity.options_entity.OptionEntity;
import ru.platform.repository.OrderServicesRepository;
import ru.platform.service.IOptionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/option")
public class OptionApi {

    private final OrderServicesRepository repository;
    private final IOptionService optionService;

    @GetMapping("/getAll")
    public List<OrderServicesEntity> getAll() {
        return repository.findAll();
    }

    @GetMapping("/getOptionsByServiceId/{id}")
    @Schema(
            description = "Получение списка опций по ID сервиса"
    )
    public List<OptionEntity> getOptionsById(@PathVariable UUID id) {
        return optionService.getOptionsByServiceId(id);
    }
}
