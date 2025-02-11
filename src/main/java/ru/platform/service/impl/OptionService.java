package ru.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.entity.options_entity.OptionEntity;
import ru.platform.repository.OptionRepository;
import ru.platform.service.IOptionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OptionService implements IOptionService {

    private final OptionRepository optionRepository;

    @Override
    public List<OptionEntity> getOptionsByServiceId(UUID serviceId) {
        return optionRepository.findAllByServiceId(serviceId);
    }
}
