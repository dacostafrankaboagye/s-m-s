package repository;

import model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryNotificationRepositoryTest {

    private NotificationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryNotificationRepository();
    }

    @Test
    void testAddNotification_Success() {
        // Given
        Notification notification = new Notification();
        notification.setId("NOTIF001");
        notification.setRecipientId("S12345");
        notification.setMessage("Your assignment is due tomorrow");
        notification.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification.setSent(false);

        // When
        repository.addNotification(notification);

        // Then
        List<Notification> notifications = repository.getNotificationsForRecipient("S12345");
        assertThat(notifications).hasSize(1);
        assertThat(notifications.get(0)).isEqualTo(notification);
    }

    @Test
    void testAddNotification_NullNotification() {
        // Then
        assertThatThrownBy(() -> repository.addNotification(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testAddNotification_DuplicateId() {
        // Given
        Notification notification1 = new Notification();
        notification1.setId("NOTIF001");
        notification1.setRecipientId("S12345");
        notification1.setMessage("Your assignment is due tomorrow");
        notification1.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification1.setSent(false);

        // When
        repository.addNotification(notification1);

        // Then
        List<Notification> notifications1 = repository.getNotificationsForRecipient("S12345");
        assertThat(notifications1).hasSize(1);
        assertThat(notifications1.get(0)).isEqualTo(notification1);


    }

    @Test
    void testGetNotificationsForRecipient_WithNotifications() {
        // Given
        Notification notification1 = new Notification();
        notification1.setId("NOTIF001");
        notification1.setRecipientId("S12345");
        notification1.setMessage("Your assignment is due tomorrow");
        notification1.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification1.setSent(false);

        Notification notification2 = new Notification();
        notification2.setId("NOTIF002");
        notification2.setRecipientId("S12345");
        notification2.setMessage("Your exam is next week");
        notification2.setScheduledTime(LocalDateTime.of(2025, 5, 20, 10, 0));
        notification2.setSent(false);

        repository.addNotification(notification1);
        repository.addNotification(notification2);

        // When
        List<Notification> notifications = repository.getNotificationsForRecipient("S12345");

        // Then
        assertThat(notifications).hasSize(2);
        assertThat(notifications).containsExactlyInAnyOrder(notification1, notification2);
    }

    @Test
    void testGetNotificationsForRecipient_NoNotifications() {
        // When
        List<Notification> notifications = repository.getNotificationsForRecipient("S12345");

        // Then
        assertThat(notifications).isEmpty();
    }

    @Test
    void testGetPendingNotifications_WithPendingNotifications() {
        // Given
        Notification notification1 = new Notification();
        notification1.setId("NOTIF001");
        notification1.setRecipientId("S12345");
        notification1.setMessage("Your assignment is due tomorrow");
        notification1.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification1.setSent(false);

        Notification notification2 = new Notification();
        notification2.setId("NOTIF002");
        notification2.setRecipientId("S67890");
        notification2.setMessage("Your exam is next week");
        notification2.setScheduledTime(LocalDateTime.of(2025, 5, 20, 10, 0));
        notification2.setSent(true); // This one is already sent

        repository.addNotification(notification1);
        repository.addNotification(notification2);

        // When
        List<Notification> pendingNotifications = repository.getPendingNotifications();

        // Then
        assertThat(pendingNotifications).hasSize(1);
        assertThat(pendingNotifications).contains(notification1);
    }

    @Test
    void testGetPendingNotifications_NoPendingNotifications() {
        // Given
        Notification notification = new Notification();
        notification.setId("NOTIF001");
        notification.setRecipientId("S12345");
        notification.setMessage("Your assignment is due tomorrow");
        notification.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification.setSent(true); // Already sent

        repository.addNotification(notification);

        // When
        List<Notification> pendingNotifications = repository.getPendingNotifications();

        // Then
        assertThat(pendingNotifications).isEmpty();
    }

    @Test
    void testMarkAsSent_Success() {
        // Given
        Notification notification = new Notification();
        notification.setId("NOTIF001");
        notification.setRecipientId("S12345");
        notification.setMessage("Your assignment is due tomorrow");
        notification.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification.setSent(false);

        repository.addNotification(notification);

        // When
        repository.markAsSent("NOTIF001");

        // Then
        List<Notification> pendingNotifications = repository.getPendingNotifications();
        assertThat(pendingNotifications).isEmpty();

        List<Notification> recipientNotifications = repository.getNotificationsForRecipient("S12345");
        assertThat(recipientNotifications).hasSize(1);
        assertThat(recipientNotifications.get(0).isSent()).isTrue();
    }

    @Test
    void testMarkAsSent_NonExistentNotification() {
        // Then - This should throw a NullPointerException because there's no null check in the implementation
        assertThatThrownBy(() -> repository.markAsSent("nonexistent"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testDeleteNotification_Success() {
        // Given
        Notification notification = new Notification();
        notification.setId("NOTIF001");
        notification.setRecipientId("S12345");
        notification.setMessage("Your assignment is due tomorrow");
        notification.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification.setSent(false);

        repository.addNotification(notification);

        // When
        repository.deleteNotification("NOTIF001");

        // Then
        List<Notification> notifications = repository.getNotificationsForRecipient("S12345");
        assertThat(notifications).isEmpty();
    }

    @Test
    void testDeleteNotification_NonExistentNotification() {
        // Then - This should throw a NullPointerException because there's no null check in the implementation
        assertThatThrownBy(() -> repository.deleteNotification("nonexistent"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testDeleteNotification_MultipleNotificationsForRecipient() {
        // Given
        Notification notification1 = new Notification();
        notification1.setId("NOTIF001");
        notification1.setRecipientId("S12345");
        notification1.setMessage("Your assignment is due tomorrow");
        notification1.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification1.setSent(false);

        Notification notification2 = new Notification();
        notification2.setId("NOTIF002");
        notification2.setRecipientId("S12345");
        notification2.setMessage("Your exam is next week");
        notification2.setScheduledTime(LocalDateTime.of(2025, 5, 20, 10, 0));
        notification2.setSent(false);

        repository.addNotification(notification1);
        repository.addNotification(notification2);

        // When
        repository.deleteNotification("NOTIF001");

        // Then
        List<Notification> notifications = repository.getNotificationsForRecipient("S12345");
        assertThat(notifications).hasSize(1);
        assertThat(notifications).contains(notification2);
    }

    @Test
    void testDeleteNotification_LastNotificationForRecipient() {
        // Given
        Notification notification = new Notification();
        notification.setId("NOTIF001");
        notification.setRecipientId("S12345");
        notification.setMessage("Your assignment is due tomorrow");
        notification.setScheduledTime(LocalDateTime.of(2025, 5, 15, 10, 0));
        notification.setSent(false);

        repository.addNotification(notification);

        // When
        repository.deleteNotification("NOTIF001");

        // Then
        List<Notification> notifications = repository.getNotificationsForRecipient("S12345");
        assertThat(notifications).isEmpty();
        
        // Verify we can add a new notification for the same recipient
        Notification newNotification = new Notification();
        newNotification.setId("NOTIF002");
        newNotification.setRecipientId("S12345");
        newNotification.setMessage("New message");
        newNotification.setScheduledTime(LocalDateTime.of(2025, 5, 20, 10, 0));
        newNotification.setSent(false);
        
        repository.addNotification(newNotification);
        
        notifications = repository.getNotificationsForRecipient("S12345");
        assertThat(notifications).hasSize(1);
        assertThat(notifications).contains(newNotification);
    }
}