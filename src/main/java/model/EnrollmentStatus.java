package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the possible enrollment statuses of a student in a course.
 *
 * <p>Each status reflects a stage or outcome in the enrollment lifecycle.
 * To be used in business logic, persistence, e.t.c where
 * enrollment state needs to be tracked or validated.</p>
 */
@Getter
@AllArgsConstructor
public enum EnrollmentStatus {
    /** Student is successfully enrolled in the course. */
    ENROLLED("E", "Student is currently enrolled"),

    /** Student has voluntarily dropped the course. */
    DROPPED("D", "Student dropped the course"),

    /** Student has successfully completed the course. */
    COMPLETED("C", "Course successfully completed"),

    /** Student attempted but failed the course. */
    FAILED("F", "Student attempted but not passed"),

    /** Student has withdrawn from the course, often after a deadline. */
    WITHDRAWN("W", "Student withdrew from the course"),

    /** Student is on the waiting list, pending available seats. */
    WAITLISTED("WL", "Student is on the waitlist");

    /** Short code for persistence or integration */
    private final String code;

    /** User-friendly description for display */
    private final String description;

    /** Precomputed lookup map for fast fromCode resolution.
     * resulting map looks:
     * CODE_MAP = {
     *     "E" -> ENROLLED,
     *     ...
     *     "WL" -> WAITLISTED
     * };

     */
    private static final Map<String, EnrollmentStatus> CODE_MAP =
            Stream.of(values())
                    .collect(Collectors.toMap(EnrollmentStatus::getCode, e->e));

    /**
     * Resolves an {@link EnrollmentStatus} by its code.
     *
     * @param code the short code (e.g., "E", "C", "W")
     * @return the matching {@link EnrollmentStatus}
     * @throws IllegalArgumentException if the code is invalid
     */
    public static EnrollmentStatus fromCode(String code){
        EnrollmentStatus status = CODE_MAP.get(code);
        if(status == null){
            throw new IllegalArgumentException("Unknown EnrollmentStatus code: " + code);
        }
        return status;
    }
}
