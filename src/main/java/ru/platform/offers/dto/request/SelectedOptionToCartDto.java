package ru.platform.offers.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SelectedOptionToCartDto {

    private String optionId;
    private JsonNode value;

    public String getValueAsString() {
        return value.isTextual() ? value.asText() : null;
    }

    public Integer getValueAsInt() {
        return value.isNumber() ? value.asInt() : null;
    }

    public List<String> getValueAsList() {
        if (value.isArray()) {
            List<String> list = new ArrayList<>();
            value.forEach(node -> list.add(node.asText()));
            return list;
        }
        return null;
    }
}
