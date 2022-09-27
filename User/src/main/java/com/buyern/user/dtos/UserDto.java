package com.buyern.user.dtos;

import com.buyern.user.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String uid;
    private String email;
    private String phone;
    private Date dob;
    private String address;
    private String address2;
    private Long city;
    private Long state;
    private Long country;
    private String image;
    private Date timeRegistered;

    @Data
    public static class UserSignInDto {
        private String email;
        private String password;
    }
    @Data
    public static class UserRegistrationDto {
        @NotEmpty(message = "First name can't be empty")
        private String firstName;
        @NotEmpty(message = "Last Name can't be empty")
        private String lastName;
        @NotEmpty(message = "Email can't be empty")
        private String email;
        @NotEmpty(message = "Password can't be empty")
        private String password;
        @NotEmpty(message = "Phone can't be empty")
        private String phone;
        @NotEmpty(message = "Date of birth can't be empty")
        private String dob;
        @NotEmpty(message = "address can't be empty")
        private String address;
        private String address2;
        @NotNull(message = "City can't be empty")
        private Long city;
        @NotNull(message = "State can't be empty")
        private Long state;
        @NotNull(message = "Country can't be empty")
        private Long country;
        @NotNull(message = "Image not uploaded")
        private MultipartFile image;
    }

    @Data
    @AllArgsConstructor
    public static class UserRegistrationSuccessDto {
        private String jwt;
        private User user;
    }
}
