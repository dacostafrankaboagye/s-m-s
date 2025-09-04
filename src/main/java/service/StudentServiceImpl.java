package service;

import lombok.AllArgsConstructor;
import model.Student;
import repository.StudentRepository;

import java.util.List;
import java.util.Map;

/**
 * Implementation of StudentService.
 *
 * Responsibilities:
 * - Delegates storage to StudentRepository.
 * - Adds validations and business logic.
 */
@AllArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Override
    public void registerStudent(Student student) {
        if (student == null || student.getId() == null || student.getEmail() == null) {
            throw new IllegalArgumentException("Student ID and email cannot be null");
        }
        studentRepository.createStudent(student);

    }

    @Override
    public Student getStudentById(String studentId) {
        return studentRepository.getById(studentId);
    }

    @Override
    public List<Student> searchStudentsByName(String token) {
        return studentRepository.searchByNameToken(token);
    }

    @Override
    public void updateContact(String studentId, String email, String phone) {

        Student student = studentRepository.getById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }
        if (email != null && !email.isBlank()) {
            student.setEmail(email);
        }
        if (phone != null && !phone.isBlank()) {
            student.setPhone(phone);
        }


    }

    @Override
    public void updateAttributes(String studentId, Map<String, String> attributes) {
        Student student = studentRepository.getById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }
        student.getAttributes().putAll(attributes);

    }

    @Override
    public void deleteStudent(String studentId) {
        studentRepository.deleteStudent(studentId);

    }
}
