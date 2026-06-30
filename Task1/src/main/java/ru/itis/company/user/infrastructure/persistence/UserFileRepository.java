package ru.itis.company.user.infrastructure.persistence;

import ru.itis.company.user.domain.User;
import ru.itis.company.user.repository.UserRepository;

import java.io.*;
import java.lang.module.FindException;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    public UserFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(user.getId() + "|" +
                    user.getEmail() + "|" +
                    user.getPassword() + "|" +
                    user.getProfileDescription());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User findById(String id) throws FindException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (id.equals(userData[0])) {
                    User user = new User(userData[0], userData[1], userData[2], userData[3]);
                    return user;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        throw new FindException("");
    }
}
