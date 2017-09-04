package ru.avtodoria.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtodoria.server.model.User;
import ru.avtodoria.server.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public int saveUser(User user) {
        User user1 = userRepository.save(user);
        return user1.getId();
    }

    @Override
    public User updateUser(User user) {
        User userToUpdate = userRepository.findOne(user.getId());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setPatronymic(user.getPatronymic());
        userToUpdate.setBirthDate(user.getBirthDate());
        User resultUser = userRepository.save(userToUpdate);
        return resultUser;
    }

    @Override
    public boolean deleteUser(User user) {
        userRepository.delete(user);
        return true;
    }
}
