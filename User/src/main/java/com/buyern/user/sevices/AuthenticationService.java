package com.buyern.user.sevices;

import com.buyern.user.dtos.authentication.UserRegistrationDto;
import com.buyern.user.dtos.response.ResponseDTO;
import com.buyern.user.exceptions.AuthenticationException;
import com.buyern.user.exceptions.EntityAlreadyExistsException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Data
@ConstructorBinding
public class AuthenticationService {
    @Value("${url.authentication-service}")
    private String authenticationServiceUrl;
    private final RestTemplate restTemplate;

    public String registerUser(UserRegistrationDto userRegistrationDto1) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<ResponseDTO.OfString> response = restTemplate.postForEntity(
                    authenticationServiceUrl + "/user/register",
                    new HttpEntity<>(userRegistrationDto1, httpHeaders),
                    ResponseDTO.OfString.class
            );
            if (Objects.requireNonNull(response.getBody()).getData().isEmpty() || Objects.requireNonNull(response.getBody()).getData().isBlank())
                throw new AuthenticationException("Error on the authentication server");
            return Objects.requireNonNull(response.getBody()).getData();

        } catch (NullPointerException ex) {
            throw new AuthenticationException("Error on the authentication server");
        } catch (HttpClientErrorException ex) {
            throw new EntityAlreadyExistsException("User with email or phone already exists");
        }
    }
}
