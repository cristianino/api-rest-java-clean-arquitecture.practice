package org.example.requests;

import lombok.Data;
import org.example.exceptions.RegisterUserException;
import org.example.repositories.UserRepository;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String username;
    private String password;

    public void validate(UserRepository userRepository){
        org.example.entities.User userExist = userRepository.getUserByUsername(username);

        if (userExist != null){
            throw new RegisterUserException("the username exist");
        }
    }
}
