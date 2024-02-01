package org.example.usecase.impl;

import org.example.models.User;
import org.example.usecase.UserTransformer;

public class RetrieveUserImpl implements UserTransformer {

    @Override
    public User apply(org.example.entities.User user) {
         return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
