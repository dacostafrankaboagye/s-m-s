package repository;

import model.Notification;

import java.util.List;

/**
 * Repository interface for managing notifications.
 *
 * Responsibilities:
 * - Store notifications.
 * - Fetch pending (unsent) notifications for processing.
 * - Update notification status (sent or not).
 * - Search notifications by recipient.
 */
public interface NotificationRepository {

    /**
     * Adds a new notification to the repository.
     *
     * @param notification the Notification object to store (must not be null).
     */
    void addNotification(Notification notification);

    /**
     * Retrieves all notifications for a specific recipient.
     *
     * @param recipientId the ID of the recipient (student or instructor).
     * @return a list of notifications for the recipient, empty if none exist.
     */
    List<Notification> getNotificationsForRecipient(String recipientId);

    /**
     * Retrieves all notifications that are pending (not yet sent).
     *
     * @return a list of pending notifications.
     */
    List<Notification> getPendingNotifications();

    /**
     * Marks a notification as sent.
     *
     * @param notificationId the ID of the notification to mark as sent.
     */
    void markAsSent(String notificationId);

    /**
     * Deletes a notification by its ID.
     *
     * @param notificationId the ID of the notification to delete.
     */
    void deleteNotification(String notificationId);
}
