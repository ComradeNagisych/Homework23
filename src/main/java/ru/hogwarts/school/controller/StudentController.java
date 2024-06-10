package ru.hogwarts.school.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }
    @GetMapping("{id}")
    public Student read(@PathVariable Long id) {
        return studentService.read(id);
    }
    @PutMapping("{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }
    @DeleteMapping("{id}")
    public Student delete(@PathVariable Long id) {
        return studentService.delete(id);
    }
    @GetMapping
    public List<Student> filterByAge(@RequestParam int age) {
        return studentService.filterByAge(age);
    }
    @GetMapping(params = {"minAge", "maxAge"})
    public List<Student> filterByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.filterByAgeBetween(minAge, maxAge);
    }
    @GetMapping("/{id}/faculty")
    public Faculty findFacultyFromStudent(@PathVariable Long id) {
        return studentService.findFacultyFromStudent(id);
    }
}
