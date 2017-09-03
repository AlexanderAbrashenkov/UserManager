package ru.avtodoria.server.service;

import ru.avtodoria.server.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    int saveUser(User user);

    User updateUser(User user);

    boolean deleteUser(User user);
}
