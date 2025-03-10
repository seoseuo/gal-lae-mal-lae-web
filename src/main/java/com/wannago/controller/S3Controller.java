package com.wannago.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import com.wannago.service.S3Service;
import com.wannago.dto.ResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filename = s3Service.saveFile(file);
            ResponseDTO responseDTO = new ResponseDTO(true,filename);
            return ResponseEntity.ok(responseDTO);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseDTO responseDTO = new ResponseDTO(false,e.getMessage());
            return ResponseEntity.ok(responseDTO);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteFile(@RequestParam("file") String file) {
        s3Service.deleteImage(file);
        ResponseDTO responseDTO = new ResponseDTO(true,"File deleted successfully");
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/download")
    public ResponseEntity<UrlResource> downloadFile(@RequestParam("file") String file) {
        try {
            UrlResource urlResource = s3Service.downloadImage(file);
            return ResponseEntity.ok(urlResource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(null);
        }
    }
    
}
