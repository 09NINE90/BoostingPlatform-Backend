package ru.platform.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDataLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule())
            .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

    public static <T> T loadFromJson(String filePath, Class<T> valueType) throws IOException {
        String content = readFile(filePath);
        return objectMapper.readValue(content, valueType);
    }

    public static <T> T loadFromJson(String filePath, TypeReference<T> typeReference) throws IOException {
        String content = readFile(filePath);
        return objectMapper.readValue(content, typeReference);
    }

    private static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/" + filePath)));
    }
}
