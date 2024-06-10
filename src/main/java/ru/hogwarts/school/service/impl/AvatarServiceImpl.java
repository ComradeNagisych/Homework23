package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarErrorException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class AvatarServiceImpl implements AvatarService {
    private String avatarDir = "./avatars";
    private final StudentService studentService;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }
    private final AvatarRepository avatarRepository;

    @Override
    public Avatar create(Long studentId, MultipartFile file) {
        try {
            byte[] data = file.getBytes();
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = UUID.fromString(file.getOriginalFilename()).toString();
            Path filePath = Path.of(avatarDir, fileName + "." + fileExtension);
            writeToFile(filePath, data);
            Student student = studentService.read(studentId);
            Avatar avatar = new Avatar();
            avatar.setData(data);
            avatar.setFilePath(filePath.toString());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setStudent(student);
            return avatarRepository.save(avatar);
        } catch (IOException exception) {
            throw new AvatarErrorException();
        }
    }
    private void writeToFile(Path path, byte[] data) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile())) {
            fileOutputStream.write(data);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    @Override
    public byte[] readFromDB(Long studentId) {
        Avatar avatar = avatarRepository.searchByStudentId(studentId);
        return avatar.getData();
    }

    @Override
    public byte[] readFromFile(Long studentId) {
        return new byte[]{};
    }

    @Override
    public Avatar getAvatarOfStudent(Long studentId) {
        return avatarRepository.searchByStudentId(studentId);
    }
}
