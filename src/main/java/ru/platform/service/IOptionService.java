package ru.platform.service;

import ru.platform.entity.options_entity.OptionEntity;

import java.util.List;
import java.util.UUID;

public interface IOptionService {

    List<OptionEntity> getOptionsByServiceId(UUID serviceId);
}
