package com.buyern.user.controllers;

import com.buyern.user.dtos.UserDto;
import com.buyern.user.models.User;
import com.buyern.user.sevices.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Data
@RestController
@RequestMapping(path = "/api/v1/")
public class UserController {
    final UserService userService;

    @GetMapping("user/get/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("user/getByUid/{uid}")
    public ResponseEntity<Optional<User>> getUserByUid(@PathVariable String uid) {
        return ResponseEntity.ok(userService.getUserByUid(uid));
    }

    @GetMapping("user/getByEmail/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("user/getByPhone/{phone}")
    public ResponseEntity<Optional<User>> getUserByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(userService.getUserByPhone(phone));
    }

    @GetMapping("users/getByIds")
    public ResponseEntity<List<User>> getUsersByIds(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(userService.getUsersByIds(ids));
    }

    @GetMapping("users/getByStateId/{stateId}")
    public ResponseEntity<List<User>> getUsersByStateId(@PathVariable Long stateId, int page, int pageSize) {
        return ResponseEntity.ok(userService.getUsersByStateId(stateId, page, pageSize));
    }

    @GetMapping("users/getByCountryId/{countryId}")
    public ResponseEntity<List<User>> getUsersByCountryId(@PathVariable Long countryId, int page, int pageSize) {
        return ResponseEntity.ok(userService.getUsersByCountryId(countryId, page, pageSize));
    }

    @PostMapping("user/register")
    public ResponseEntity<UserDto.UserRegistrationSuccessDto> registerUser(@Valid @ModelAttribute UserDto.UserRegistrationDto user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("user/signIn")
    public ResponseEntity<Optional<User>> signIn(@Valid @RequestBody UserDto.UserSignInDto userSignInDetails) {
        return ResponseEntity.ok(userService.signIn(userSignInDetails));
    }
}
