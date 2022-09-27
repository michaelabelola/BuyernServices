package com.buyern.fileservice.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUploadDto {
    private String name;
    private MultipartFile file;
}
