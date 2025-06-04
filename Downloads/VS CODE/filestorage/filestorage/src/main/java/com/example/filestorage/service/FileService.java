package com.example.filestorage.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.filestorage.dto.FileDTO;

public interface FileService {
    FileDTO uploadFile(MultipartFile file);
    List<FileDTO> getAllFiles();
    FileDTO getFileById(Long id);
}