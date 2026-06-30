package ru.itis.company.user.infrastructure.persistence;

import ru.itis.company.user.domain.User;
import ru.itis.company.user.repository.UserRepository;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;
    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {

                String[] parts = line.split("\\|");

                User user = new User(parts[0], parts[1], parts[2], parts[3]);

                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }

                line = br.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getId().equals(id)) {
                    return Optional.of(user);
                }

                line = br.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public boolean updateUserData(String email, String profileDescription) {
        Optional<User> userOptional = findByEmail(email);

        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        user.setProfileDescription(profileDescription);
        return updateUserDataInFile(user);

    }

    private boolean updateUserDataInFile(User userUpdate) {

        File sourceFile = new File(fileName);
        File tempFile = new File(fileName + ".tmp");

        boolean isUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;

            while ((line = br.readLine()) != null) {
                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(userUpdate.getEmail())) {
                    bw.write(userMapper.toLine(userUpdate));
                    isUpdated = true;
                } else {
                    bw.write(line);
                }

                bw.newLine();
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        if (!isUpdated) {
            tempFile.delete();
            return false;
        }

        if (!sourceFile.delete()) {
            throw new IllegalStateException("Не удалось удалить старый файл");
        }

        if (!tempFile.renameTo(sourceFile)) {
            throw new IllegalStateException("Не удалось переименовать временный файл");
        }
        return true;
    }


}
