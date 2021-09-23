package com.github.alextheartisan.boox.user.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * User
 *
 * @todo Security Stamp
 * @todo Two Factor Authorization
 * @todo OTAC (One-Time Access Code)
 */
public class User {

    public int id;

    public String login;
    public String password; // hashed

    public String email;
    public String phone;
    public Gender gender;
    public String firstName;
    public String lastName;
    public LocalDate birthDate;

    public LocalDateTime firstLoggedInAt;
    public LocalDateTime lastLoggedInAt;
    public LocalDateTime registeredAt;
    public LocalDateTime deletedAt; // TODO: nullable

    public Status status;

    public User() {
        registeredAt = LocalDateTime.now();
    }

    /**
     * Mark user as deleted
     */
    public void delete() {
        email = null;
        phone = null;

        deletedAt = LocalDateTime.now();
    }
}
