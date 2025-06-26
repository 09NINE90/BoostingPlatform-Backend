package ru.platform.utils;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class GenerateSecondIdUtil {

    public String getRandomId(){
        return String.valueOf(generateId().insert(4,"-")).toUpperCase(Locale.ROOT);
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

    public static String toRandomLookingId(long num) {
        long mixed = (num ^ 0x5E5E5E5EL) * 0xDEADBEEFL;
        mixed = mixed ^ (mixed >>> 16);

        return toCustomId(mixed & 0xFFFFFFFFL);
    }

    private static String toCustomId(long num) {
        int lettersPart = (int) ((num >> 16) & 0xFFFF);
        int numbersPart = (int) (num & 0xFFFF);

        String letters = toCustomBase(lettersPart, 26, 4, 'A');
        String numbers = String.format("%04d", numbersPart % 10000);

        return letters + "-" + numbers;
    }

    private static String toCustomBase(int num, int radix, int length, char firstChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = num % radix;
            sb.append((char) (firstChar + digit));
            num /= radix;
        }
        return sb.reverse().toString();
    }

}
