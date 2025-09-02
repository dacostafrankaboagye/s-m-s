package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot implements Comparable<TimeSlot> {

    /** Day of the week (e.g., MONDAY, TUESDAY). */
    private DayOfWeek dayOfWeek;

    /** Start time of the slot (e.g., 09:00). */
    private LocalTime startTime;

    /** End time of the slot (e.g., 10:30). */
    private LocalTime endTime;

    /**
     * Defines natural ordering of time slots.
     *
     * <p>Comparison order:
     * <ol>
     *   <li>By day of week (Monday &lt; Tuesday &lt; ... &lt; Sunday)</li>
     *   <li>By start time</li>
     *   <li>By end time</li>
     * </ol>
     * </p>
     *
     * @param o another time slot
     * @return negative if this slot is before, positive if after, 0 if equal
     */
    @Override
    public int compareTo(TimeSlot o) {

        int dayComparison = this.dayOfWeek.compareTo(o.dayOfWeek);
        if(dayComparison !=0 ){return dayComparison;}

        int startComparison = this.startTime.compareTo(o.startTime);
        if(startComparison !=0 ){return startComparison;}

        return this.endTime.compareTo(o.endTime);
    }

    /**
     * Human-readable representation of the time slot.
     *
     * @return formatted string (e.g., "MONDAY 09:00–10:30")
     */
    @Override
    public String toString() {
        return String.format("%s %s–%s", dayOfWeek, startTime, endTime);
    }
}
