package model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the different types of grades a student can receive.
 *
 * <p>Each grade type has:
 * <ul>
 *   <li>a short {@code code} for persistence or integration</li>
 *   <li>a human-readable {@code label}</li>
 * </ul>
 *
 */
@Getter
@AllArgsConstructor
public enum GradeType {

    /** Assignment grade (e.g., homework, lab work). */
    ASSIGNMENT("A", "Assigment"),

    /** Quiz grade, usually short assessments. */
    QUIZ("Q", "Quiz"),

    /** Midterm exam grade. */
    MIDTERM("M", "Midterm Exam"),

    /** Final exam grade. */
    FINAL("F", "Final Exam"),

    /** Project or practical work grade. */
    PROJECT("P", "Project");

    /** Precomputed lookup map for fast fromCode resolution. */
    private static final Map<String, GradeType> CODE_MAP =
            Stream.of(values())
                    .collect(Collectors.toMap(GradeType::getCode, e->e));


    /**
     * Resolves a {@link GradeType} by its code.
     *
     * @param code the short code (e.g., "A", "M", "F")
     * @return the matching {@link GradeType}
     * @throws IllegalArgumentException if the code is invalid
     */
    public static GradeType fromCode(String code){
        GradeType type = CODE_MAP.get(code);
        if(type == null){
            throw new IllegalArgumentException("Unknown GradeType code: " + code);
        }
        return type;
    }


    /** Short code for persistence or integration. */
    private final String code;

    /** User-friendly label for display. */
    private final String label;
}
