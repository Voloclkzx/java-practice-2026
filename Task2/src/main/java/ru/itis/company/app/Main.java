package ru.itis.company.app;

import ru.itis.company.user.api.UserConsoleOperations;
import ru.itis.company.user.application.UserService;
import ru.itis.company.user.infrastructure.persistence.UserFileRepository;
import ru.itis.company.user.infrastructure.persistence.UserMapper;
import ru.itis.company.user.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        UserRepository userFileRepository = new UserFileRepository("users.txt", new UserMapper());
        UserService userService = new UserService(userFileRepository);
        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
