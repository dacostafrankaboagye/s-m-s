package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GradeTypeTest {

    @Test
    void testEnumValues() {
        // Verify all expected enum values exist
        assertThat(GradeType.values()).hasSize(5);
        assertThat(GradeType.valueOf("ASSIGNMENT")).isEqualTo(GradeType.ASSIGNMENT);
        assertThat(GradeType.valueOf("QUIZ")).isEqualTo(GradeType.QUIZ);
        assertThat(GradeType.valueOf("MIDTERM")).isEqualTo(GradeType.MIDTERM);
        assertThat(GradeType.valueOf("FINAL")).isEqualTo(GradeType.FINAL);
        assertThat(GradeType.valueOf("PROJECT")).isEqualTo(GradeType.PROJECT);
    }

    @Test
    void testEnumProperties() {
        // Verify properties of each enum value
        assertThat(GradeType.ASSIGNMENT.getCode()).isEqualTo("A");
        assertThat(GradeType.ASSIGNMENT.getLabel()).isEqualTo("Assigment");

        assertThat(GradeType.QUIZ.getCode()).isEqualTo("Q");
        assertThat(GradeType.QUIZ.getLabel()).isEqualTo("Quiz");

        assertThat(GradeType.MIDTERM.getCode()).isEqualTo("M");
        assertThat(GradeType.MIDTERM.getLabel()).isEqualTo("Midterm Exam");

        assertThat(GradeType.FINAL.getCode()).isEqualTo("F");
        assertThat(GradeType.FINAL.getLabel()).isEqualTo("Final Exam");

        assertThat(GradeType.PROJECT.getCode()).isEqualTo("P");
        assertThat(GradeType.PROJECT.getLabel()).isEqualTo("Project");
    }

    @ParameterizedTest
    @MethodSource("provideGradeTypeCodesAndExpectedEnums")
    void testFromCodeValid(String code, GradeType expectedType) {
        // When
        GradeType type = GradeType.fromCode(code);

        // Then
        assertThat(type).isEqualTo(expectedType);
    }

    private static Stream<Arguments> provideGradeTypeCodesAndExpectedEnums() {
        return Stream.of(
                Arguments.of("A", GradeType.ASSIGNMENT),
                Arguments.of("Q", GradeType.QUIZ),
                Arguments.of("M", GradeType.MIDTERM),
                Arguments.of("F", GradeType.FINAL),
                Arguments.of("P", GradeType.PROJECT)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "", "invalid", "a", "q", "m"})
    void testFromCodeInvalid(String invalidCode) {
        // Then
        assertThatThrownBy(() -> GradeType.fromCode(invalidCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown GradeType code: " + invalidCode);
    }
}