package ru.platform.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.platform.entity.UserEntity;
import ru.platform.entity.enums.ERatingCustomer;
import ru.platform.entity.enums.ERoles;
import ru.platform.repository.UserRepository;

import java.time.LocalDate;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoaderConfig implements CommandLineRunner {

    @Override
    public void run(java.lang.String... args) {
//        repository.save(UserEntity.builder()
//                        .username("test@user.com")
//                        .password(encoder.encode("qweasd123"))
//                        .roles(ERoles.CUSTOMER.getTitle())
//                        .rating(ERatingCustomer.LEVEL_1.getTitle())
//                        .lastActivityAt(LocalDate.now())
//                        .createdAt(LocalDate.now())
//                        .ordersCount(0)
//                        .secondId(getRandomId())
//                        .build());
        log.info("New user created success!");
    }


}
