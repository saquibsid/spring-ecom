package com.ecommerce.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

interface FileService {
    String uploadImage(String path, MultipartFile file) throws IOException;
}
