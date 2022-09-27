package com.buyern.fileservice.services;

import com.buyern.fileservice.dtos.UserFileUploadDto;
import com.buyern.fileservice.exceptions.RecordNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@Data
public class UserFileService {
    final FileService fileService;
    @Value("${storage.container.users}")
    String usersContainerName;

    public String uploadUserProfilePicture(UserFileUploadDto userFileUploadDto) {
        return fileService.uploadToContainer(usersContainerName, userFileUploadDto.getFile(), fileService.generateFileLink(new String[]{userFileUploadDto.getUserUid()}, "profileImage", Objects.requireNonNull(userFileUploadDto.getFile().getContentType()).split("/")[1]));
    }
    public String uploadUserProfilePicture(String userId, File file, @Nullable String contentType) {
        return fileService.uploadToContainer(usersContainerName, file, fileService.generateFileLink(new String[]{userId}, "profileImage", contentType));
    }

}
