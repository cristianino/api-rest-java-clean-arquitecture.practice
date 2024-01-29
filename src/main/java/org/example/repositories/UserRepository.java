package org.example.repositories;

import org.example.entities.User;

public interface UserRepository {
    void saveUser(User user);
    User getUserByID(int id);
    User getUserByUsername(String username);
    void deleteUser(int id);
}
