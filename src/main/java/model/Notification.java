package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a notification to be sent to a student or instructor.
 *
 * <p>Notifications can be related to course enrollment, grades, deadlines, etc.
 * This model is useful for background job processing and reminders.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    /** Unique ID for the notification (UUID or generated string). */
    private String id;

    /** Recipient ID (could be studentId or instructorId). */
    private String recipientId;

    /** The actual message content. */
    private String message;

    /** Timestamp when the notification should be delivered. */
    private LocalDateTime scheduledTime;

    /** Whether the notification has been sent. */
    private boolean sent;
}
