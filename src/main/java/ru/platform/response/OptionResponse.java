package ru.platform.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OptionResponse {

    private UUID id;
    private String title;
    private String type;
    private List<String> options;
    private List<Double> prices;

}
