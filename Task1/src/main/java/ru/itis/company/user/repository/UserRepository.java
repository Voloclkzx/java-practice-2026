package ru.itis.company.user.repository;

import ru.itis.company.user.domain.User;

public interface UserRepository {

    void save(User user);

    User findById(String id);

}
