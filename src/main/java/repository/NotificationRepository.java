package repository;

import model.Notification;

import java.util.List;

/**
 * Repository interface for managing Notification entities.
 *
 * Responsibilities:
 * - Store and retrieve notifications.
 * - Find pending notifications for processing.
 */
public interface NotificationRepository {

    /**
     * Creates and stores a new notification.
     *
     * @param notification the Notification object to add
     */
    void createNotification(Notification notification);

    /**
     * Retrieves a notification by its unique ID.
     *
     * @param id the notification ID
     * @return the Notification object, or null if not found
     */
    Notification getById(String id);

    /**
     * Lists all notifications that are scheduled but not yet sent.
     *
     * @return a list of pending notifications
     */
    List<Notification> findPendingNotifications();
}
