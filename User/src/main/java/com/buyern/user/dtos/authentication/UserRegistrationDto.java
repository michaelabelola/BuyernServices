package com.buyern.user.dtos.authentication;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String email;
    private String phone;
    private String password;
    private String uid;
    private Long id;
}
