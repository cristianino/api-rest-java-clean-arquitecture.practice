package org.example.requests;

import lombok.Data;
import org.example.exceptions.RegisterUserException;
import org.example.repositories.UserRepository;

@Data
public class LoginRequest {
    private String username;
    private String password;

    public void validate(UserRepository userRepository){
        org.example.entities.User userExist = userRepository.getUserByUsername(username);

        if (userExist == null){
            throw new RegisterUserException("user no found");
        }
    }
}
