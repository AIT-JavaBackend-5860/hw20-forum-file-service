package ait.cohort5860.post.controller;

import ait.cohort5860.post.dto.FileResponseDto;
import ait.cohort5860.post.model.FileEntity;
import ait.cohort5860.post.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        FileResponseDto dto = fileService.storeFile(file);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        FileEntity file = fileService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file.getData());
    }
}