package org.example.services.auth.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.repositories.impl.UserRepositoryImpl;
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

        org.example.entities.User userExist = userRepository.getUserByUsername(user.getUsername());

        if (userExist != null){
            throw new JWTVerificationException("the username exist");
        }


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
