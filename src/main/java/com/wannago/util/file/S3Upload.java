package com.wannago.util.file;

import org.springframework.web.multipart.MultipartFile;
import com.wannago.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

public class S3Upload {

    @Autowired
    private S3Service s3Service;

    public void saveFileToS3(MultipartFile file, String fileName) {
        try {
            MultipartFile renamedFile = new ByteArrayMultipartFile(
                fileName,                         // 새로운 파일명
                fileName,                         // 원본 파일명 (새 이름 적용)
                file.getContentType(),            // 기존 파일의 ContentType 유지
                file.getBytes()                   // 기존 파일의 데이터 유지
            );

            // 변경된 파일을 S3에 저장
            s3Service.saveFile(renamedFile);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }
}