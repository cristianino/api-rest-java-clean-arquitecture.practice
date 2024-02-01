package org.example.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
public class User {
    private UUID id;
    private String username;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;
}
