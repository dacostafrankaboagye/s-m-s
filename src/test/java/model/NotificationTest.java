package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

class NotificationTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Notification notification = new Notification();

        // Then
        assertThat(notification.getId()).isNull();
        assertThat(notification.getRecipientId()).isNull();
        assertThat(notification.getMessage()).isNull();
        assertThat(notification.getScheduledTime()).isNull();
        assertThat(notification.isSent()).isFalse();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String id = "NOTIF001";
        String recipientId = "S12345";
        String message = "Your assignment is due tomorrow";
        LocalDateTime scheduledTime = LocalDateTime.of(2025, 5, 15, 10, 0);
        boolean sent = false;

        // When
        Notification notification = new Notification(id, recipientId, message, scheduledTime, sent);

        // Then
        assertThat(notification.getId()).isEqualTo(id);
        assertThat(notification.getRecipientId()).isEqualTo(recipientId);
        assertThat(notification.getMessage()).isEqualTo(message);
        assertThat(notification.getScheduledTime()).isEqualTo(scheduledTime);
        assertThat(notification.isSent()).isEqualTo(sent);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Notification notification = new Notification();
        String id = "NOTIF001";
        String recipientId = "S12345";
        String message = "Your assignment is due tomorrow";
        LocalDateTime scheduledTime = LocalDateTime.of(2025, 5, 15, 10, 0);
        boolean sent = true;

        // When
        notification.setId(id);
        notification.setRecipientId(recipientId);
        notification.setMessage(message);
        notification.setScheduledTime(scheduledTime);
        notification.setSent(sent);

        // Then
        assertThat(notification.getId()).isEqualTo(id);
        assertThat(notification.getRecipientId()).isEqualTo(recipientId);
        assertThat(notification.getMessage()).isEqualTo(message);
        assertThat(notification.getScheduledTime()).isEqualTo(scheduledTime);
        assertThat(notification.isSent()).isEqualTo(sent);
    }
}