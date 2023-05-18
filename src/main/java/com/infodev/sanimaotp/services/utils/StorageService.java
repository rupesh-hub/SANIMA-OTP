package com.infodev.sanimaotp.services.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
public class StorageService {


    @Value("${qrCodeFolder}")
    private String filePath;

//   private Path rootLocation=Paths.get("C:/apache-tomcat-8.5.9/webapps/sanimaotp/WEB-INF/classes/static/qrCodes/");
//   private Path rootLocation=Paths.get("E:/sanimaotp/apache-tomcat-8.5.34/webapps/sanimaotp/WEB-INF/classes/static/qrCodes/");



    public String storeDPXFile(String fileId, MultipartFile file){

        Path fileLocation =  Paths.get(filePath);
        try {
            String fileName=fileId;
            Path path=fileLocation.resolve(fileId);
            Files.copy(file.getInputStream(),path );
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File cannot be uploaded " +e.getMessage());
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = Paths.get(filePath).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
            	throw new RuntimeException("File path not found!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        	throw new RuntimeException(e.getMessage());
        }
    }
    
    public void deleteFile(MultipartFile file) throws IOException {
        Resource storedFile= this.loadFile(file.getOriginalFilename());
        File mf= storedFile.getFile();
        mf.delete();
    }

    public void init() {
        try {
            Files.createDirectory(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}