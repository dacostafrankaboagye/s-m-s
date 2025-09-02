package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;

class TimeSlotTest {

    @Test
    void testNoArgsConstructor() {
        // When
        TimeSlot timeSlot = new TimeSlot();

        // Then
        assertThat(timeSlot.getDayOfWeek()).isNull();
        assertThat(timeSlot.getStartTime()).isNull();
        assertThat(timeSlot.getEndTime()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 30);

        // When
        TimeSlot timeSlot = new TimeSlot(dayOfWeek, startTime, endTime);

        // Then
        assertThat(timeSlot.getDayOfWeek()).isEqualTo(dayOfWeek);
        assertThat(timeSlot.getStartTime()).isEqualTo(startTime);
        assertThat(timeSlot.getEndTime()).isEqualTo(endTime);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        TimeSlot timeSlot = new TimeSlot();
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 30);

        // When
        timeSlot.setDayOfWeek(dayOfWeek);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);

        // Then
        assertThat(timeSlot.getDayOfWeek()).isEqualTo(dayOfWeek);
        assertThat(timeSlot.getStartTime()).isEqualTo(startTime);
        assertThat(timeSlot.getEndTime()).isEqualTo(endTime);
    }

    @Test
    void testCompareToDifferentDays() {
        // Given
        TimeSlot mondaySlot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeSlot tuesdaySlot = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));

        // Then
        assertThat(mondaySlot.compareTo(tuesdaySlot)).isNegative();
        assertThat(tuesdaySlot.compareTo(mondaySlot)).isPositive();
    }

    @Test
    void testCompareToDifferentStartTimes() {
        // Given
        TimeSlot earlySlot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeSlot lateSlot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 30));

        // Then
        assertThat(earlySlot.compareTo(lateSlot)).isNegative();
        assertThat(lateSlot.compareTo(earlySlot)).isPositive();
    }

    @Test
    void testCompareToDifferentEndTimes() {
        // Given
        TimeSlot shortSlot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));
        TimeSlot longSlot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0));

        // Then
        assertThat(shortSlot.compareTo(longSlot)).isNegative();
        assertThat(longSlot.compareTo(shortSlot)).isPositive();
    }

    @Test
    void testCompareToEqual() {
        // Given
        TimeSlot slot1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeSlot slot2 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));

        // Then
        assertThat(slot1.compareTo(slot2)).isZero();
        assertThat(slot2.compareTo(slot1)).isZero();
    }

    @Test
    void testToString() {
        // Given
        TimeSlot timeSlot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));

        // When
        String result = timeSlot.toString();

        // Then
        assertThat(result).isEqualTo("MONDAY 09:00–10:30");
    }
}