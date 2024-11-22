package ru.platform.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateSecondIdUtil {

    public String getRandomId(){
        String readyId = String.valueOf(generateId().insert(3,"-"));

        return readyId;
    }

    private StringBuilder generateId(){
        StringBuilder randomId = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            randomId.append(random.nextInt(10));
        }
        return randomId;
    }
}
