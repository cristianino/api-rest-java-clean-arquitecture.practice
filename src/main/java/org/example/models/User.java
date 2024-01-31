package org.example.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String name;
    private String email;
}
