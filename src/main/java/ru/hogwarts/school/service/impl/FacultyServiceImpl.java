package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setName(faculty.getName());
                    oldFaculty.setColor(faculty.getColor());
                    return facultyRepository.save(oldFaculty);
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Faculty delete(Long id) {
        return facultyRepository.findById(id)
                .map(faculty -> {
                    facultyRepository.delete(faculty);
                    return faculty;
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public List<Faculty> filterByNameOrColor(String nameOrColor) {
        return facultyRepository.filterByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    @Override
    public List<Student> findStudentsFromFaculty(Long facultyId) {
        return studentRepository.filterByFaculty_Id(facultyId);
    }
}
