package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarErrorException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }
    @PostMapping(value = "/student/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@PathVariable("id") Long studentId, MultipartFile file) {
        avatarService.create(studentId, file);
    }
    @GetMapping(value = "/student/{id}/avatar/from-db")
    public ResponseEntity<byte[]> downloadFromDB(@PathVariable("id") Long studentId) {
        Avatar avatar = avatarService.getAvatarOfStudent(studentId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(avatar.getMediaType())).contentLength(avatar.getFileSize()).body(avatar.getData());
    }
    @GetMapping(value = "/student/{id}/avatar/from-file")
    public ResponseEntity<byte[]> downloadFromFile(@PathVariable("id") Long studentId) {
        Avatar avatar = avatarService.getAvatarOfStudent(studentId);
        try (FileInputStream fileInputStream = new FileInputStream(Path.of(avatar.getFilePath()).toFile())) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(avatar.getMediaType())).contentLength(avatar.getFileSize()).body(fileInputStream.readAllBytes());
        } catch (IOException e) {
            throw new AvatarErrorException();
        }
    }
}
