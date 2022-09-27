package com.buyern.fileservice.services;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.IOException;

@Service
@ConstructorBinding
public class FileService {
    String azureBlobConnString;
    BlobServiceClient blobServiceClient;
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService(@Value("${spring.cloud.azure.storage.blob.connection-string}") String azureBlobConnString) {
        this.azureBlobConnString = azureBlobConnString;
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(azureBlobConnString)
                .buildClient();
    }

    public BlobContainerClient getContainerClient(String containerName) {
        //container name must be at least 3 characters long
        if (containerName.length() == 1) containerName = "00" + containerName;
        else if (containerName.length() == 2) containerName = "0" + containerName;
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public BlobContainerClient createContainerClient(String containerName) {
        //container name must be at least 3 characters long
        if (containerName.length() == 1) containerName = "00" + containerName;
        else if (containerName.length() == 2) containerName = "0" + containerName;
        try {
            return blobServiceClient.createBlobContainerWithResponse(containerName, null, null, Context.NONE).getValue();
        } catch (Exception ex) {
            throw new ServerErrorException("Can't create storage account for entity", ex);
        }
    }

    private void deleteContainer(BlobContainerClient blobContainerClient) {
        try {
            blobContainerClient.delete();
            logger.info("Delete completed%n");
        } catch (BlobStorageException error) {
            if (error.getErrorCode().equals(BlobErrorCode.CONTAINER_NOT_FOUND)) {
                logger.info("Delete failed. Container was not found %n");
            }
        }
    }

    public String upload(BlobContainerClient containerClient, MultipartFile file, String destinationFile) {
        try {
            BlobClient blobClient = blobClient(containerClient, destinationFile);
            blobClient.upload(file.getInputStream(), file.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("can't convert file to stream");
        } catch (Exception ex) {
            return null;
//            throw new RuntimeException("Error uploading to storage server");
        }
    }

    public String upload(BlobContainerClient containerClient, File file, String destinationFile) {
        try {
            BlobClient blobClient = blobClient(containerClient, destinationFile);
            blobClient.uploadFromFile(file.getPath(), true);
            return blobClient.getBlobUrl();
        } catch (Exception ex) {
            return null;
//            throw new RuntimeException("Error uploading to storage server");
        }
    }

    public BlobContainerClient containerClient(String containerName) {
        try {
            return blobServiceClient.createBlobContainer(containerName);
        } catch (Exception ex) {
            return blobServiceClient.getBlobContainerClient(containerName);
        }
    }

    // Get a reference to a blob
    public BlobClient blobClient(BlobContainerClient containerClient, String fileName) {
        return containerClient.getBlobClient(fileName);
    }

    public String generateFileLink(String fileName, String[] folders) {
        StringBuilder _folder = new StringBuilder();
        for (String folder : folders) {
            _folder.append(folder).append("/");
        }
        _folder.append(fileName);
        return _folder.toString();
    }

    public String generateFileLink(String[] folders, String fileName, String fileExtension) {
        StringBuilder _folder = new StringBuilder();
        for (String folder : folders) {
            _folder.append(folder).append("/");
        }
        _folder.append(fileName);
        _folder.append("." + fileExtension);
        return _folder.toString();
    }

    public String generateFileLink(String fileName) {
        return "/" + fileName;
    }

    public boolean uploadFile(BlobClient blobClient, String pathToFile) throws IOException {
        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
// Upload the blob
        blobClient.uploadFromFile(pathToFile);
        return true;
    }

    public PagedIterable<BlobItem> listBlob(BlobContainerClient containerClient) {
        return containerClient.listBlobs();
    }

    /**
     * <h3>Upload file to Container</h3>
     *
     * @param containerName the name of the container the file will be uploaded to.
     * @param destination   path the file will be located at
     * @param file          the file to be uploaded
     */
    public String uploadToContainer(String containerName, MultipartFile file, String destination) {
        String uploadedFileUrl = upload(getContainerClient(containerName), file, destination);
        if (uploadedFileUrl == null)
            return upload(createContainerClient(containerName), file, destination);
        return uploadedFileUrl;
    }

    /**
     * <h3>Upload file to Container</h3>
     *
     * @param containerName the name of the container the file will be uploaded to.
     * @param destination   path the file will be located at
     * @param file          the file to be uploaded
     */
    public String uploadToContainer(String containerName, File file, String destination) {
        String uploadedFileUrl = upload(getContainerClient(containerName), file, destination);
        if (uploadedFileUrl == null)
            return upload(createContainerClient(containerName), file, destination);
        System.out.println(uploadedFileUrl);
        return uploadedFileUrl;
    }

//    public String uploadToEntityContainer(long entityId, MultipartFile file, String destination) {
//        String uploadedFileUrl = upload(getContainerClient(String.valueOf(entityId)), file, destination);
//        if (uploadedFileUrl == null)
//            return upload(createContainerClient(String.valueOf(entityId)), file, destination);
//        return uploadedFileUrl;
//    }
//
//    public String uploadToEntityContainer(long entityId, File file, String destination) {
//        String uploadedFileUrl = upload(getContainerClient(String.valueOf(entityId)), file, destination);
//        if (uploadedFileUrl == null)
//            return upload(createContainerClient(usersContainerName), file, destination);
//        return uploadedFileUrl;
//    }
}