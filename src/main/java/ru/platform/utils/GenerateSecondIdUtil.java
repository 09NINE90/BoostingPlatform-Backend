package ru.platform.utils;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class GenerateSecondIdUtil {

    public String getRandomId(){
        String readyId = String.valueOf(generateId().insert(4,"-")).toUpperCase(Locale.ROOT);

        return readyId;
    }

    private StringBuilder generateId(){
        StringBuilder randomId = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++){
            randomId.append((char) (Math.random() * 26 + 'a'));
        }
        for (int i = 0; i < 4; i++){
            randomId.append(random.nextInt(10));
        }
        return randomId;
    }
}
