package service;

import model.GradeType;
import model.dto.StudentGpa;

import java.util.List;
import java.util.Map;

public interface ReportService {

    /**
     * Computes the GPA for a given student.
     *
     * @param studentId the unique ID of the student.
     * @return the GPA (0.0 - 4.0), or 0.0 if no enrollments.
     */
    double computeGpa(String studentId);

    /**
     * Returns the top N students by GPA.
     *
     * @param n number of top students to retrieve.
     * @return list of StudentGpa objects sorted by GPA descending.
     */
    List<StudentGpa> topNStudentsByGpa(int n);

    /**
     * Returns grade distribution for a course in a given semester.
     *
     * @param courseCode the course code (e.g., CS101).
     * @param semester   the semester (e.g., FALL2025).
     * @return map of GradeType to count.
     */
    Map<GradeType, Integer> gradeDistribution(String courseCode, String semester);

    /**
     * Calculates attendance percentage for a student in a course.
     *
     * @param studentId  the student ID.
     * @param courseCode the course code.
     * @param semester   the semester.
     * @return attendance percentage (0.0 - 100.0).
     */
    double attendancePercentage(String studentId, String courseCode, String semester);

    /**
     * Exports a report (e.g., GPA report, top students, grade distribution) to a file.
     *
     * @param reportType the type of report (e.g., "GPA", "TOP_N").
     * @param filePath   destination file path.
     */
    void exportReport(String reportType, String filePath);
}
