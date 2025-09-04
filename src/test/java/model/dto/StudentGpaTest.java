package model.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentGpaTest {

    @Test
    void constructor_shouldCreateStudentGpaWithAllFields() {
        StudentGpa studentGpa = new StudentGpa("S123", "John Doe", 3.75);
        
        assertThat(studentGpa.studentId()).isEqualTo("S123");
        assertThat(studentGpa.fullName()).isEqualTo("John Doe");
        assertThat(studentGpa.gpa()).isEqualTo(3.75);
    }

    @Test
    void equals_shouldReturnTrueForSameValues() {
        StudentGpa gpa1 = new StudentGpa("S123", "John Doe", 3.75);
        StudentGpa gpa2 = new StudentGpa("S123", "John Doe", 3.75);
        
        assertThat(gpa1).isEqualTo(gpa2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentValues() {
        StudentGpa gpa1 = new StudentGpa("S123", "John Doe", 3.75);
        StudentGpa gpa2 = new StudentGpa("S124", "Jane Doe", 3.50);
        
        assertThat(gpa1).isNotEqualTo(gpa2);
    }

    @Test
    void toString_shouldContainAllFields() {
        StudentGpa studentGpa = new StudentGpa("S123", "John Doe", 3.75);
        String toString = studentGpa.toString();
        
        assertThat(toString).contains("S123");
        assertThat(toString).contains("John Doe");
        assertThat(toString).contains("3.75");
    }
}