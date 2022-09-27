package com.buyern.fileservice.controllers;

import com.buyern.fileservice.dtos.ResponseDTO;
import com.buyern.fileservice.dtos.UserFileUploadDto;
import com.buyern.fileservice.services.UserFileService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Data
@RestController
@RequestMapping("api/v1/")
public class FileController {
    final UserFileService userFileService;

    @PostMapping("user/profileImage")
    public ResponseEntity<Optional<String>> userUploadProfileImage(@ModelAttribute UserFileUploadDto fileUploadDto) {
        return ResponseEntity.ok(Optional.of(userFileService.uploadUserProfilePicture(fileUploadDto)));
    }

    @PostMapping("user/profileImageByteArray")
    public ResponseEntity<ResponseDTO> userUploadProfileImage(@RequestBody UserFileUploadDto.UserFileUploadByByteArrayDto fileUploadDto) {
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), fileUploadDto.getUserUid() + "-profileImage");
        try {
            Files.write(path, fileUploadDto.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(userFileService.uploadUserProfilePicture(fileUploadDto.getUserUid(), path.toFile(), fileUploadDto.getContentType())).build());
    }
}
