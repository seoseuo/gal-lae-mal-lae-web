package com.wannago.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    // 파일 업로드
    // 중복이름 처리 uuid 검색해보기
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        return originalFilename;
    }

    // 파일 다운로드
    public UrlResource downloadImage(String originalFilename) throws MalformedURLException {
        return new UrlResource(amazonS3.getUrl(bucket, originalFilename));
    }

    public String getContentDisposition(String originalFilename) {
        return "attachment; filename=\"" + originalFilename + "\"";
    }

    // 이미지 미리보기
    public String viewFile(String filename) {
        String url = amazonS3.getUrl(bucket, filename).toString();
        return url;
    }

    // 파일 삭제
    public void deleteImage(String originalFilename) {
        amazonS3.deleteObject(bucket, originalFilename);
    }

}
