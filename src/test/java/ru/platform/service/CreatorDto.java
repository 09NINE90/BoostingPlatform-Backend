package ru.platform.service;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.platform.exception.PlatformException;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.dto.response.GameBySecondIdRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.orders.dao.OrderEntity;
import ru.platform.orders.dto.response.OrderRsDto;
import ru.platform.user.dao.UserEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.platform.exception.ErrorType.JSON_LOAD_ERROR;

public class CreatorDto {

    private static final String ENTITIES_PATH = "entities/";
    private static final String DTOS_PATH = "dtos/";

    /**
     * Генерация списка сущностей игр из БД
     */
    public static List<GameEntity> getListOfGamesEntity() throws IOException {
        return TestDataLoader.loadFromJson(ENTITIES_PATH + "games_list.json", new TypeReference<>() {
        });
    }

    /**
     * Генерация сущности игры из БД
     */
    public static Optional<GameEntity> getGameEntity() {
        GameEntity game;
        try {
            game = TestDataLoader.loadFromJson(ENTITIES_PATH + "game_entity.json", GameEntity.class);
        } catch (IOException e) {
            throw new PlatformException(JSON_LOAD_ERROR);
        }
        return Optional.of(game);
    }

    /**
     * Генерация списка игр для side bar
     */
    public static List<GameMainPageRsDto> getGameMainPageRsDto() {
        try {
            return TestDataLoader.loadFromJson(DTOS_PATH + "game_main_page_rs_dto_list.json",
                    new TypeReference<>() {
                    });
        } catch (IOException e) {
            throw new PlatformException(JSON_LOAD_ERROR);
        }
    }

    /**
     * Генерация объекта игра, найденной по id
     */
    public static GameBySecondIdRsDto getGameBySecondIdRsDto() {
        try {
            return TestDataLoader.loadFromJson(DTOS_PATH + "game_by_second_id_rs_dto.json",
                    GameBySecondIdRsDto.class);
        } catch (IOException e) {
            throw new PlatformException(JSON_LOAD_ERROR);
        }
    }

    /**
     * Получение сущности пользователя заказчика из БД
     */
    public static UserEntity getCustomerUserEntity() {
        try {
            return TestDataLoader.loadFromJson(ENTITIES_PATH + "user_entity_customer.json", UserEntity.class);
        } catch (IOException e) {
            throw new PlatformException(JSON_LOAD_ERROR);
        }
    }

    /**
     * Получение сущности пользователя админа из БД
     */
    public static UserEntity getAdminUserEntity() {
        try {
            return TestDataLoader.loadFromJson(ENTITIES_PATH + "user_entity_admin.json", UserEntity.class);
        } catch (IOException e) {
            throw new PlatformException(JSON_LOAD_ERROR);
        }
    }

    /**
     * Генерация списка сущностей заказов из БД
     */
    public static List<OrderEntity> getListOfOrdersEntity() {
        try {
            return TestDataLoader.loadFromJson(ENTITIES_PATH + "orders_list.json",
                    new TypeReference<>() {});
        } catch (IOException e) {
            throw new PlatformException(JSON_LOAD_ERROR);
        }
    }

    /**
     * Генерация объекта ордера для ответа фронту
     */
    public static OrderRsDto getOrderRsDto() {
        try {
            return TestDataLoader.loadFromJson(DTOS_PATH + "order_rs_dto.json", OrderRsDto.class);
        } catch (IOException e) {
            throw new PlatformException(JSON_LOAD_ERROR);
        }
    }
}
