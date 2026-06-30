package company.user.api;

import ru.itis.company.user.domain.User;
import ru.itis.company.user.repository.UserRepository;

import java.lang.module.FindException;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserRepository userRepository;
    private final Scanner scanner;

    public UserConsoleOperations(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("0. Выход");

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                System.out.println("Сейчас будем регистрировать пользователя");
                System.out.println("Введите email:");
                String email = scanner.nextLine();
                System.out.println("Введите password:");
                String password = scanner.nextLine();
                System.out.println("Введите описание профиля:");
                String profileDescription = scanner.nextLine();
                User user = new User(email, password, profileDescription);
                userRepository.save(user);
            }
            break;
            case "2": {
                System.out.println("Вы можете войти в приложение");
            }
            break;
            case "3": {
                System.out.println("Ищем пользователя по его id");
                String id = scanner.nextLine();
                try {
                    User userFound = userRepository.findById(id);
                    System.out.println(userFound.getEmail());

                } catch (FindException e) {
                    System.out.println("Пользователь не найден");
                }
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }
}
