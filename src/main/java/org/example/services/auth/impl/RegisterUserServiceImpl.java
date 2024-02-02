package org.example.services.auth.impl;

import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.services.auth.RegisterUserService;
import org.example.usecase.UserTransformer;
import org.example.usecase.impl.RetrieveUserImpl;
import org.example.utils.HashUtil;

public class RegisterUserServiceImpl implements RegisterUserService {
    private final UserRepository userRepository;

    public RegisterUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User register(User user, String password) {
        UserTransformer userTransformer  = new RetrieveUserImpl();

        String hashedPassword = HashUtil.hashString(password);

        org.example.entities.User userEntity = new org.example.entities.User();
        userEntity.setUsername(user.getUsername());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(hashedPassword);

        userRepository.saveUser(userEntity);

        userEntity = userRepository.getUserByUsername(userEntity.getUsername());

        return userTransformer.apply(userEntity);
    }
}
