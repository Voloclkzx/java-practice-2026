package ru.itis.company.user.repository;

import ru.itis.company.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    //TODO доделать сохранение изменения в файл
    boolean updateUserData(String email, String profileDescription);
}
