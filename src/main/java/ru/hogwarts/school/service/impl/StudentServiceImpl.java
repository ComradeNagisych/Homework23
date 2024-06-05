package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student update(Long id, Student student) {
        return studentRepository.findById(id)
                .map(oldStudent -> {
                    oldStudent.setName(student.getName());
                    oldStudent.setAge(student.getAge());
                    return studentRepository.save(oldStudent);
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student delete(Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return student;
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public List<Student> filterByAge(int age) {
        return studentRepository.filterByAge(age);
    }

    @Override
    public List<Student> filterByAgeBetween(int minAge, int maxAge) {
        return studentRepository.filterByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty findFacultyFromStudent(Long id) {
        return read(id).getFaculty();
    }
}
