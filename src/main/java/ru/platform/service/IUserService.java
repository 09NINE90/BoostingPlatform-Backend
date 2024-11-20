package ru.platform.service;

import ru.platform.entity.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> getAllUsers();
}
