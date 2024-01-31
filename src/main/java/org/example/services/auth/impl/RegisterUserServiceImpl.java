package org.example.services.auth.impl;

import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.repositories.impl.UserRepositoryImpl;
import org.example.services.auth.RegisterUserService;
import org.example.usecase.UserTransformer;
import org.example.usecase.impl.RetrieveUserImpl;

public class RegisterUserServiceImpl implements RegisterUserService {
    @Override
    public User register(User user, String password) {
        UserRepository userRepository = new UserRepositoryImpl();
        UserTransformer userTransformer  = new RetrieveUserImpl();

        org.example.entities.User userEntity = new org.example.entities.User();
        userEntity.setUsername(user.getUsername());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(password);

        userRepository.saveUser(userEntity);

        userEntity = userRepository.getUserByUsername(userEntity.getUsername());

        return userTransformer.apply(userEntity);
    }
}
