package ru.avtodoria.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avtodoria.server.model.User;
import ru.avtodoria.server.service.UserService;
import ru.avtodoria.shared.dto.UserDto;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usermanager")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsersList() throws ParseException {
        List<User> savedUsers = userService.getAllUsers();

        List<UserDto> userDtoList = savedUsers.stream()
                .map(user -> new UserDto(user.getId(), user.getLastName(), user.getFirstName(), user.getPatronymic(), user.getBirthDate()))
                .collect(Collectors.toList());

        return new ResponseEntity<List<UserDto>>(userDtoList, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Integer> saveNewUser (@RequestBody UserDto userDto) {
        User user = new User(userDto);
        int id = userService.saveUser(user);
        return new ResponseEntity<Integer>(id, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser (@RequestBody UserDto userDto) {
        User user = new User(userDto);
        User resultUser = userService.updateUser(user);
        UserDto resultUserDto = new UserDto(resultUser.getId(), resultUser.getLastName(), resultUser.getFirstName(), resultUser.getPatronymic(), resultUser.getBirthDate());
        return new ResponseEntity<UserDto>(resultUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Boolean> deleteUser (@RequestBody UserDto userDto) {
        User user = new User(userDto);
        boolean result = userService.deleteUser(user);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}
