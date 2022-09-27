package com.buyern.user.sevices;

import com.buyern.user.dtos.UserDto;
import com.buyern.user.dtos.authentication.UserRegistrationDto;
import com.buyern.user.exceptions.BadRequestException;
import com.buyern.user.models.User;
import com.buyern.user.models.UserContact;
import com.buyern.user.models.UserLocation;
import com.buyern.user.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final AuthenticationService authenticationService;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<User> getUsersByStateId(Long stateId, int page, int pageSize) {
        return userRepository.findByState(stateId, Pageable.ofSize(pageSize).withPage(page));
    }

    public List<User> getUsersByCountryId(Long countryId, int page, int pageSize) {
        return userRepository.findByCountry(countryId, Pageable.ofSize(pageSize).withPage(page));
    }

    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Transactional
    public UserDto.UserRegistrationSuccessDto registerUser(UserDto.UserRegistrationDto userRegistrationDto) {

        if (!MediaService.isImage(userRegistrationDto.getImage().getContentType()))
            throw new BadRequestException("File is not an Image");
        User newUser = new User();
        UserLocation location = new UserLocation();
        UserContact contact = new UserContact();
        newUser.setFirstName(userRegistrationDto.getFirstName());
        newUser.setLastName(userRegistrationDto.getLastName());
        userRepository.saveAndFlush(newUser);

        contact.setUser(newUser);
        contact.setEmail(userRegistrationDto.getEmail());
        contact.setPhone(userRegistrationDto.getPhone());
        try {
            contact.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(userRegistrationDto.getDob().strip()));
        } catch (ParseException e) {
            throw new BadRequestException("Date of birth is in bad format");
        }

        location.setUser(newUser);
        location.setAddress(userRegistrationDto.getAddress());
        location.setAddress2(userRegistrationDto.getAddress2());
        location.setCity(userRegistrationDto.getCity());
        location.setState(userRegistrationDto.getState());
        location.setCountry(userRegistrationDto.getCountry());

        newUser.setLocation(location);
        newUser.setContact(contact);
        UserRegistrationDto userRegistrationDto1 = new UserRegistrationDto();
        userRegistrationDto1.setEmail(contact.getEmail());
        userRegistrationDto1.setPassword(userRegistrationDto.getPassword());
        userRegistrationDto1.setPhone(contact.getPhone());
        userRegistrationDto1.setId(newUser.getId());
        userRegistrationDto1.setUid(newUser.getUid());

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        String jwt = authenticationService.registerUser(userRegistrationDto1);
        newUser.setImage(fileService.uploadProfileImage(userRegistrationDto.getImage(), newUser.getUid()));
        objectNode.putPOJO("user", newUser);

        return new UserDto.UserRegistrationSuccessDto(jwt, newUser);
    }

    public Optional<User> signIn(UserDto.UserSignInDto userSignInDetails) {
        return null;
    }
}
