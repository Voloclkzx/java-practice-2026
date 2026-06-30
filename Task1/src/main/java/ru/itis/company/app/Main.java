package ru.itis.company.app;

import ru.itis.company.user.api.UserConsoleOperations;
import ru.itis.company.user.infrastructure.persistence.UserFileRepository;

public class Main {
    public static void main(String[] args) {
        UserFileRepository userFileRepository = new UserFileRepository("users.txt");
        UserConsoleOperations operations = new UserConsoleOperations(userFileRepository);

        while (true) {
            operations.showMenu();
        }
    }
}
