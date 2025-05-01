package ru.platform.offers.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.platform.offers.enumz.SectionType;

import java.util.List;

@Data
@Builder
public class OfferSectionRsDto {

    private String title;
    private SectionType type;
    private String description;
    private List<OfferSectionItemRsDto> items;
}
