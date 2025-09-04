package service;

import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentRepository);
    }

    @Test
    void registerStudent_ValidStudent_CallsRepository() {
        Student student = Student.builder()
                .id("S001")
                .fullName("John Doe")
                .email("john@example.com")
                .build();

        studentService.registerStudent(student);

        verify(studentRepository).createStudent(student);
    }

    @Test
    void registerStudent_NullStudent_ThrowsException() {
        assertThatThrownBy(() -> studentService.registerStudent(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID and email cannot be null");
    }

    @Test
    void registerStudent_NullId_ThrowsException() {
        Student student = Student.builder()
                .fullName("John Doe")
                .email("john@example.com")
                .build();

        assertThatThrownBy(() -> studentService.registerStudent(student))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID and email cannot be null");
    }

    @Test
    void registerStudent_NullEmail_ThrowsException() {
        Student student = Student.builder()
                .id("S001")
                .fullName("John Doe")
                .build();

        assertThatThrownBy(() -> studentService.registerStudent(student))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID and email cannot be null");
    }

    @Test
    void getStudentById_ValidId_ReturnsStudent() {
        String studentId = "S001";
        Student expectedStudent = Student.builder()
                .id(studentId)
                .fullName("John Doe")
                .email("john@example.com")
                .build();

        when(studentRepository.getById(studentId)).thenReturn(expectedStudent);

        Student result = studentService.getStudentById(studentId);

        assertThat(result).isEqualTo(expectedStudent);
        verify(studentRepository).getById(studentId);
    }

    @Test
    void searchStudentsByName_ValidToken_ReturnsStudents() {
        String token = "john";
        List<Student> expectedStudents = List.of(
                Student.builder().id("S001").fullName("John Doe").email("john@example.com").build(),
                Student.builder().id("S002").fullName("Johnny Smith").email("johnny@example.com").build()
        );

        when(studentRepository.searchByNameToken(token)).thenReturn(expectedStudents);

        List<Student> result = studentService.searchStudentsByName(token);

        assertThat(result).isEqualTo(expectedStudents);
        verify(studentRepository).searchByNameToken(token);
    }

    @Test
    void updateContact_ValidStudentAndContact_UpdatesStudent() {
        String studentId = "S001";
        String newEmail = "newemail@example.com";
        String newPhone = "123-456-7890";
        
        Student student = Student.builder()
                .id(studentId)
                .fullName("John Doe")
                .email("old@example.com")
                .phone("000-000-0000")
                .build();

        when(studentRepository.getById(studentId)).thenReturn(student);

        studentService.updateContact(studentId, newEmail, newPhone);

        assertThat(student.getEmail()).isEqualTo(newEmail);
        assertThat(student.getPhone()).isEqualTo(newPhone);
        verify(studentRepository).getById(studentId);
    }

    @Test
    void updateContact_StudentNotFound_ThrowsException() {
        String studentId = "S999";
        when(studentRepository.getById(studentId)).thenReturn(null);

        assertThatThrownBy(() -> studentService.updateContact(studentId, "email@example.com", "123-456-7890"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student not found");
    }

    @Test
    void updateContact_NullEmail_OnlyUpdatesPhone() {
        String studentId = "S001";
        String newPhone = "123-456-7890";
        
        Student student = Student.builder()
                .id(studentId)
                .fullName("John Doe")
                .email("old@example.com")
                .phone("000-000-0000")
                .build();

        when(studentRepository.getById(studentId)).thenReturn(student);

        studentService.updateContact(studentId, null, newPhone);

        assertThat(student.getEmail()).isEqualTo("old@example.com");
        assertThat(student.getPhone()).isEqualTo(newPhone);
    }

    @Test
    void updateContact_BlankEmail_OnlyUpdatesPhone() {
        String studentId = "S001";
        String newPhone = "123-456-7890";
        
        Student student = Student.builder()
                .id(studentId)
                .fullName("John Doe")
                .email("old@example.com")
                .phone("000-000-0000")
                .build();

        when(studentRepository.getById(studentId)).thenReturn(student);

        studentService.updateContact(studentId, "  ", newPhone);

        assertThat(student.getEmail()).isEqualTo("old@example.com");
        assertThat(student.getPhone()).isEqualTo(newPhone);
    }

    @Test
    void updateAttributes_ValidStudentAndAttributes_UpdatesAttributes() {
        String studentId = "S001";
        Map<String, String> newAttributes = Map.of(
                "major", "Computer Science",
                "year", "Senior"
        );
        
        Student student = Student.builder()
                .id(studentId)
                .fullName("John Doe")
                .email("john@example.com")
                .attributes(new HashMap<>())
                .build();

        when(studentRepository.getById(studentId)).thenReturn(student);

        studentService.updateAttributes(studentId, newAttributes);

        assertThat(student.getAttributes()).containsAllEntriesOf(newAttributes);
        verify(studentRepository).getById(studentId);
    }

    @Test
    void updateAttributes_StudentNotFound_ThrowsException() {
        String studentId = "S999";
        Map<String, String> attributes = Map.of("major", "Computer Science");
        
        when(studentRepository.getById(studentId)).thenReturn(null);

        assertThatThrownBy(() -> studentService.updateAttributes(studentId, attributes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student not found");
    }

    @Test
    void deleteStudent_ValidId_CallsRepository() {
        String studentId = "S001";

        studentService.deleteStudent(studentId);

        verify(studentRepository).deleteStudent(studentId);
    }
}