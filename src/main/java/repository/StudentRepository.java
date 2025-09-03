package repository;

import model.Student;

import java.util.List;

public interface StudentRepository {

    void createStudent(Student student);
    Student getById(String id);
    List<Student> searchByNameToken(String token);
    void deleteStudent(String id);
}
