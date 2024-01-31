package org.example.services.auth.impl;

import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.repositories.impl.UserRepositoryImpl;
import org.example.services.auth.RegisterUserService;

public class RegisterUserServiceImpl implements RegisterUserService {
    @Override
    public User register(User user, String password) {
        UserRepository userRepository = new UserRepositoryImpl();

        org.example.entities.User userEntity = new org.example.entities.User();
        userEntity.setUsername(user.getUsername());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(password);

        userRepository.saveUser(userEntity);

        userEntity = userRepository.getUserByUsername(userEntity.getUsername());

        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .build();
    }
}
