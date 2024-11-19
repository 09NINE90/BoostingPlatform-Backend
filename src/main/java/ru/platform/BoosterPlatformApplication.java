package ru.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.util.UUID;

@SpringBootApplication
public class BoosterPlatformApplication {

    public static void main(String[] args) {
        String uuidString = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        UUID uuid = UUID.fromString(uuidString);

        BigInteger decimalUUID = new BigInteger(uuid.toString().replace("-", ""), 16); // Преобразование из 16-ричной в 10-ричную

        System.out.println("Decimal representation (BigInteger): " + decimalUUID);
        SpringApplication.run(BoosterPlatformApplication.class, args);
    }

}
