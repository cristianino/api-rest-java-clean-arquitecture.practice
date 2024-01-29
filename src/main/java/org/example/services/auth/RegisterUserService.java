package org.example.services.auth;

import org.example.models.User;

public interface RegisterUserService {
    User register(User user, String password);
}
