package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();
    private static Long currentId = 0L;
    @Override
    public Student create(Student student) {
        student.setId(++currentId);
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student read(Long id) {
        return studentMap.get(id);
    }

    @Override
    public Student update(Long id, Student student) {
        Student existingStudent = studentMap.get(id);
        existingStudent.setAge(student.getAge());
        existingStudent.setName(student.getName());
        return existingStudent;
    }

    @Override
    public Student delete(Long id) {
        return studentMap.remove(id);
    }

    @Override
    public List<Student> filterByAge(int age) {
        return studentMap.values()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
