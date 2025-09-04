package service;

import lombok.AllArgsConstructor;
import model.GradeType;
import model.dto.StudentGpa;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.StudentRepository;

import java.util.List;
import java.util.Map;

/**
 * Implementation of ReportService for generating academic reports.
 *
 * Features:
 * - GPA calculation
 * - Top N students by GPA
 * - Grade distribution for a course
 * - Attendance percentage
 * - Report export to CSV
 */
@AllArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    @Override
    public double computeGpa(String studentId) {
        return 0;
    }

    @Override
    public List<StudentGpa> topNStudentsByGpa(int n) {
        return List.of();
    }

    @Override
    public Map<GradeType, Integer> gradeDistribution(String courseCode, String semester) {
        return Map.of();
    }

    @Override
    public double attendancePercentage(String studentId, String courseCode, String semester) {
        return 0;
    }

    @Override
    public void exportReport(String reportType, String filePath) {

    }
}
