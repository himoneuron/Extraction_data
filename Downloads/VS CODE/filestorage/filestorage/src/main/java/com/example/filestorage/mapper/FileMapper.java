package com.example.filestorage.mapper;

import com.example.filestorage.dto.FileDTO;
import com.example.filestorage.entity.FileEntity;

public class FileMapper {

    public static FileDTO toDTO(FileEntity entity) {
        return FileDTO.builder()
                .id(entity.getId())
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .fileSize(entity.getFileSize())
                .fileUrl("/files/" + entity.getId()) // simple REST path
                .build();
    }

    public static FileEntity toEntity(FileDTO dto) {
        return FileEntity.builder()
                .id(dto.getId())
                .fileName(dto.getFileName())
                .fileType(dto.getFileType())
                .fileSize(dto.getFileSize())
                .build();
    }
}
