package com.coderc.ltsn.controller;

import com.coderc.ltsn.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
public class MinioController {

    @Autowired
    MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        var rs = new HashMap<>();
        try {
            String message = minioService.uploadFile(file);
            rs.put("message", message);
            rs.put("status", "200");
            return ResponseEntity.ok(rs);
        } catch (Exception e) {
            rs.put("message", e.getMessage());
            rs.put("status", "500");
            return ResponseEntity.status(500).body(rs);
        }
    }

    @GetMapping("/url")
    public ResponseEntity<?> getFileUrl(@RequestParam("fileName") String fileName) {
        var rs = new HashMap<>();
        try {
            String fileUrl = minioService.getFileUrl(fileName);
            rs.put("message", fileUrl);
            rs.put("status", "200");
            return ResponseEntity.ok(rs);
        } catch (Exception e) {
            rs.put("message", e.getMessage());
            rs.put("status", "500");
            return ResponseEntity.status(500).body(rs);
        }
    }
}