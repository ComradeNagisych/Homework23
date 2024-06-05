package ru.hogwarts.school.exception;


public class FacultyNotFoundException extends NotFoundException {
    private final long id;
    public FacultyNotFoundException(long id) {
        this.id = id;
    }
    @Override
    public String getMessage() {
        return "Faculty with id = %d is not found!".formatted(id);
    }
}
