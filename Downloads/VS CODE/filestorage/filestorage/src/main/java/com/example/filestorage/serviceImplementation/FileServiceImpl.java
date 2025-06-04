package com.example.filestorage.serviceImplementation;




import com.example.filestorage.dto.FileDTO;
import com.example.filestorage.entity.FileEntity;
import com.example.filestorage.mapper.FileMapper;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final String uploadDir = "./uploads"; // Consider loading this from properties

    @Override
    public FileDTO uploadFile(MultipartFile file) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(file.getOriginalFilename());
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            FileEntity saved = fileRepository.save(FileEntity.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(filePath.toString())
                    .uploadedAt(LocalDateTime.now())
                    .build());

            return FileMapper.toDTO(saved);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public List<FileDTO> getAllFiles() {
        return fileRepository.findAll()
                .stream()
                .map(FileMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FileDTO getFileById(Long id) {
        return fileRepository.findById(id)
                .map(FileMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}
