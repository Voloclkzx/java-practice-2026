package ru.itis.company.user.api;

import ru.itis.company.user.application.UserService;
import ru.itis.company.user.domain.User;
import ru.itis.company.user.repository.UserRepository;

import java.lang.module.FindException;
import java.util.Optional;
import java.util.Scanner;

public class UserConsoleOperations {
    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findById();
            }
            break;
            case "4": {
                updateUserData();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить данные пользователя");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();
        userService.signUp(email, password, profileDescription);
    }

    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        if (userService.signIn(email, password)) {
            System.out.println("Вы успешно вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void findById() {
        System.out.println("Ищем пользователя по его id");
        System.out.println("Введите id пользователя: ");
        String id = scanner.nextLine();
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            String email = userOptional.get().getEmail();
            System.out.printf("Пользователь с id: %s найден: %s\n", id, email);
        } else {
            System.out.println("Пользователь с таким id не найден");
        }
    }

    private void updateUserData() {
        System.out.println("Обновляем данные пользователя");
        System.out.println("Введите email: ");
        String email = scanner.nextLine();
        System.out.println("Введите описание профиля: ");
        String profileDescription = scanner.nextLine();
        if (userService.updateUserData(email, profileDescription)) {
            System.out.println("Описание профиля успешно изменено");
        } else {
            System.out.println("Не удалось изменить описание профиля, пользователь с таким email не найден");
        }
    }























}
