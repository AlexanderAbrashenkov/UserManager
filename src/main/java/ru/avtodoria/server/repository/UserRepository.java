package ru.avtodoria.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtodoria.server.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
