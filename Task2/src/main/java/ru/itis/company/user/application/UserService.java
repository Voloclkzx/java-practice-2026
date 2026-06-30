package ru.itis.company.user.application;

import ru.itis.company.user.domain.User;
import ru.itis.company.user.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional.map(user -> user.getPassword().equals(password)).orElse(false);

    }
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public boolean updateUserData(String email, String profileDescription) {
        return userRepository.updateUserData(email, profileDescription);
    }











}
