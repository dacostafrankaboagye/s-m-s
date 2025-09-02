package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnrollmentStatusTest {

    @Test
    void testEnumValues() {
        // Verify all expected enum values exist
        assertThat(EnrollmentStatus.values()).hasSize(6);
        assertThat(EnrollmentStatus.valueOf("ENROLLED")).isEqualTo(EnrollmentStatus.ENROLLED);
        assertThat(EnrollmentStatus.valueOf("DROPPED")).isEqualTo(EnrollmentStatus.DROPPED);
        assertThat(EnrollmentStatus.valueOf("COMPLETED")).isEqualTo(EnrollmentStatus.COMPLETED);
        assertThat(EnrollmentStatus.valueOf("FAILED")).isEqualTo(EnrollmentStatus.FAILED);
        assertThat(EnrollmentStatus.valueOf("WITHDRAWN")).isEqualTo(EnrollmentStatus.WITHDRAWN);
        assertThat(EnrollmentStatus.valueOf("WAITLISTED")).isEqualTo(EnrollmentStatus.WAITLISTED);
    }

    @Test
    void testEnumProperties() {
        // Verify properties of each enum value
        assertThat(EnrollmentStatus.ENROLLED.getCode()).isEqualTo("E");
        assertThat(EnrollmentStatus.ENROLLED.getDescription()).isEqualTo("Student is currently enrolled");

        assertThat(EnrollmentStatus.DROPPED.getCode()).isEqualTo("D");
        assertThat(EnrollmentStatus.DROPPED.getDescription()).isEqualTo("Student dropped the course");

        assertThat(EnrollmentStatus.COMPLETED.getCode()).isEqualTo("C");
        assertThat(EnrollmentStatus.COMPLETED.getDescription()).isEqualTo("Course successfully completed");

        assertThat(EnrollmentStatus.FAILED.getCode()).isEqualTo("F");
        assertThat(EnrollmentStatus.FAILED.getDescription()).isEqualTo("Student attempted but not passed");

        assertThat(EnrollmentStatus.WITHDRAWN.getCode()).isEqualTo("W");
        assertThat(EnrollmentStatus.WITHDRAWN.getDescription()).isEqualTo("Student withdrew from the course");

        assertThat(EnrollmentStatus.WAITLISTED.getCode()).isEqualTo("WL");
        assertThat(EnrollmentStatus.WAITLISTED.getDescription()).isEqualTo("Student is on the waitlist");
    }

    @ParameterizedTest
    @MethodSource("provideStatusCodesAndExpectedEnums")
    void testFromCodeValid(String code, EnrollmentStatus expectedStatus) {
        // When
        EnrollmentStatus status = EnrollmentStatus.fromCode(code);

        // Then
        assertThat(status).isEqualTo(expectedStatus);
    }

    private static Stream<Arguments> provideStatusCodesAndExpectedEnums() {
        return Stream.of(
                Arguments.of("E", EnrollmentStatus.ENROLLED),
                Arguments.of("D", EnrollmentStatus.DROPPED),
                Arguments.of("C", EnrollmentStatus.COMPLETED),
                Arguments.of("F", EnrollmentStatus.FAILED),
                Arguments.of("W", EnrollmentStatus.WITHDRAWN),
                Arguments.of("WL", EnrollmentStatus.WAITLISTED)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "", "invalid", "e", "d", "c"})
    void testFromCodeInvalid(String invalidCode) {
        // Then
        assertThatThrownBy(() -> EnrollmentStatus.fromCode(invalidCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown EnrollmentStatus code: " + invalidCode);
    }
}