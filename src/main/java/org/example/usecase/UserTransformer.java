package org.example.usecase;

import org.example.models.User;

import java.util.function.Function;

public interface UserTransformer extends Function<org.example.entities.User, User> {
}
