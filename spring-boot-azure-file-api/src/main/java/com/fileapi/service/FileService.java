package com.fileapi.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.fileapi.property.FileStorageProperties;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Service
public class FileService {

    private final Path fileStorageLocation;
    private final BlobServiceClient blobServiceClient;

    @Autowired
    public FileService(@NonNull FileStorageProperties fileStorageProperties, BlobServiceClient blobServiceClient) {
        // get the path of the upload directory
        fileStorageLocation = Path.of(fileStorageProperties.getUploadDir());
        this.blobServiceClient = blobServiceClient;
        try {
            // creates directory/directories, if directory already exists, it will not throw exception
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            log.error("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public Path getFileStorageLocation() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            log.error("Could not create the directory where the uploaded file will be stored.", e);
        }
        return fileStorageLocation;
    }

    public Boolean uploadAndDownloadFile(@NonNull MultipartFile file, String containerName) {
        boolean isSuccess = true;
        BlobContainerClient blobContainerClient = getBlobContainerClient(containerName);
        String filename = file.getOriginalFilename();
        BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(filename).getBlockBlobClient();
        try {
            // delete file if already exists in that container
            if (blockBlobClient.exists()) {
                blockBlobClient.delete();
            }
            // upload file to azure blob storage
            blockBlobClient.upload(new BufferedInputStream(file.getInputStream()), file.getSize(), true);
            String tempFilePath = fileStorageLocation + "/" + filename;
            Files.deleteIfExists(Paths.get(tempFilePath));
            // download file from azure blob storage to a file
            blockBlobClient.downloadToFile(new File(tempFilePath).getPath());
        } catch (IOException e) {
            isSuccess = false;
            log.error("Error while processing file {}", e.getLocalizedMessage());
        }
        return isSuccess;
    }


    private @NonNull BlobContainerClient getBlobContainerClient(@NonNull String containerName) {
        // create container if not exists
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!blobContainerClient.exists()) {
            blobContainerClient.create();
        }
        return blobContainerClient;
    }
}
