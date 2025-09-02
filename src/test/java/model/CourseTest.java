package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

class CourseTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Course course = new Course();

        // Then
        assertThat(course.getCode()).isNull();
        assertThat(course.getTitle()).isNull();
        assertThat(course.getCredits()).isEqualTo(0);
        assertThat(course.getDepartment()).isNull();
        assertThat(course.getPrerequisites()).isNotNull().isEmpty();
        assertThat(course.getScheduledSlots()).isNotNull().isEmpty();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String code = "CS101";
        String title = "Introduction to Computer Science";
        int credits = 3;
        String department = "Computer Science";
        Set<String> prerequisites = new HashSet<>();
        prerequisites.add("MATH101");
        Set<TimeSlot> scheduledSlots = new TreeSet<>();
        scheduledSlots.add(new TimeSlot(java.time.DayOfWeek.MONDAY, 
                           java.time.LocalTime.of(9, 0), 
                           java.time.LocalTime.of(10, 30)));

        // When
        Course course = new Course(code, title, credits, department, prerequisites, scheduledSlots);

        // Then
        assertThat(course.getCode()).isEqualTo(code);
        assertThat(course.getTitle()).isEqualTo(title);
        assertThat(course.getCredits()).isEqualTo(credits);
        assertThat(course.getDepartment()).isEqualTo(department);
        assertThat(course.getPrerequisites()).isEqualTo(prerequisites);
        assertThat(course.getScheduledSlots()).isEqualTo(scheduledSlots);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Course course = new Course();
        String code = "CS101";
        String title = "Introduction to Computer Science";
        int credits = 3;
        String department = "Computer Science";
        Set<String> prerequisites = new HashSet<>();
        prerequisites.add("MATH101");
        Set<TimeSlot> scheduledSlots = new TreeSet<>();
        scheduledSlots.add(new TimeSlot(java.time.DayOfWeek.MONDAY, 
                           java.time.LocalTime.of(9, 0), 
                           java.time.LocalTime.of(10, 30)));

        // When
        course.setCode(code);
        course.setTitle(title);
        course.setCredits(credits);
        course.setDepartment(department);
        course.setPrerequisites(prerequisites);
        course.setScheduledSlots(scheduledSlots);

        // Then
        assertThat(course.getCode()).isEqualTo(code);
        assertThat(course.getTitle()).isEqualTo(title);
        assertThat(course.getCredits()).isEqualTo(credits);
        assertThat(course.getDepartment()).isEqualTo(department);
        assertThat(course.getPrerequisites()).isEqualTo(prerequisites);
        assertThat(course.getScheduledSlots()).isEqualTo(scheduledSlots);
    }

    @Test
    void testPrerequisitesCollection() {
        // Given
        Course course = new Course();
        Set<String> prerequisites = course.getPrerequisites();

        // When
        prerequisites.add("MATH101");
        prerequisites.add("ENG101");
        prerequisites.add("MATH101"); // Duplicate should be ignored

        // Then
        assertThat(course.getPrerequisites()).hasSize(2);
        assertThat(course.getPrerequisites()).contains("MATH101", "ENG101");
    }

    @Test
    void testScheduledSlotsCollection() {
        // Given
        Course course = new Course();
        Set<TimeSlot> scheduledSlots = course.getScheduledSlots();
        TimeSlot mondaySlot = new TimeSlot(java.time.DayOfWeek.MONDAY, 
                               java.time.LocalTime.of(9, 0), 
                               java.time.LocalTime.of(10, 30));
        TimeSlot wednesdaySlot = new TimeSlot(java.time.DayOfWeek.WEDNESDAY, 
                                 java.time.LocalTime.of(9, 0), 
                                 java.time.LocalTime.of(10, 30));

        // When
        scheduledSlots.add(mondaySlot);
        scheduledSlots.add(wednesdaySlot);

        // Then
        assertThat(course.getScheduledSlots()).hasSize(2);
        assertThat(course.getScheduledSlots()).contains(mondaySlot, wednesdaySlot);
        
        // Verify natural ordering (Monday should come before Wednesday)
        assertThat(course.getScheduledSlots().iterator().next()).isEqualTo(mondaySlot);
    }
}