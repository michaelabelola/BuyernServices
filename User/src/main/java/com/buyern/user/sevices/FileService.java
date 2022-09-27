package com.buyern.user.sevices;

import com.buyern.user.dtos.response.ResponseDTO;
import com.buyern.user.exceptions.BadRequestException;
import com.buyern.user.exceptions.FileServiceException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
@Data
@ConstructorBinding
public class FileService {
    private final RestTemplate restTemplate;
    @Value("${url.file-service}")
    private String fileServiceUrl;

    public String uploadProfileImage(MultipartFile file, String userUid) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        restTemplate.getMessageConverters().add(messageConverter);
        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put("userUid", userUid);
        try {
            body.put("file", file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        body.put("contentType", Objects.requireNonNull(file.getContentType(), "Error getting file content type").split("/")[1]);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LinkedHashMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<ResponseDTO.OfString> response = restTemplate.postForEntity(fileServiceUrl + "/user/profileImageByteArray", requestEntity, ResponseDTO.OfString.class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new FileServiceException("Error Uploading file to file server");
        }
        return Objects.requireNonNull(response.getBody(), "Error While uploading File").getData();
    }
}
