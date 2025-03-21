package com.example.Portfolio_Onboard.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileController {

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {

        try {
            // 기본 저장 폴더 경로 (업로드 시 사용한 경로와 동일)
            Path filePath = Paths.get("/app/data/file").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // MIME 타입 결정. 알 수 없으면 application/octet-stream 사용.
                String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                // 다운로드 시 파일명이 올바르게 설정되도록 header에 Content-Disposition 추가
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (MalformedURLException e) {
            // URL 형식이 잘못된 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            // 파일 MIME 타입 결정 중 오류가 발생한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload-image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("upload") MultipartFile file) {

        Map<String, Object> response = new HashMap<>();

        try {

            String fileName = file.getOriginalFilename();
            String extension = "";

            if (fileName != null && fileName.lastIndexOf('.') != -1) {

                extension = fileName.substring(fileName.lastIndexOf('.')); // 원본 파일명에서 확장자 추출
            }

            String newFileName = UUID.randomUUID() + extension; // UUID를 사용하여 새로운 파일명 생성

            // String newFileName = UUID.randomUUID().toString() + extension;

            File destDir = new File("C://data/image");
            //File destDir = new File("/app/data/image");
            if (!destDir.exists()) {

                destDir.mkdirs(); // 디렉토리 및 하위 디렉토리 생성, 이미 존재하는 디렉토리는 자동으로 빼고 생성해 준다.
            }

            File dest = new File("C://data/image/" + newFileName);
            //File dest = new File("/app/data/image/" + newFileName); // 파일을 저장할 때는 반드시 파일명까지 포함된 경로를 지정해야 한다.
            file.transferTo(dest); // 실제로 파일 저장을 실행.

            String imageUrl = "/uploads/" + newFileName;
            response.put("url", imageUrl);
        } catch (Exception e) {

            response.put("error", Collections.singletonMap("message", "이미지 업로드 실패"));
        }

        return response;
    }
}
