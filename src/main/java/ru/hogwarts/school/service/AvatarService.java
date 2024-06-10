package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

public interface AvatarService {
    Avatar create(Long studentId, MultipartFile file);
    byte[] readFromDB(Long studentId);
    byte[] readFromFile(Long studentId);
    Avatar getAvatarOfStudent(Long studentId);
}
