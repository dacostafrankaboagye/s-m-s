package repository;

import lombok.NonNull;
import model.Notification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the {@link NotificationRepository} interface.
 *
 * Responsibilities:
 * - Store notifications in memory.
 * - Provide quick lookups by recipient ID.
 * - Fetch pending notifications for background processing.
 *
 * Data Structures:
 * - notificationsById: ConcurrentHashMap for O(1) lookups by notification ID.
 * - notificationsByRecipient: HashMap with HashSet for recipient-based lookups.
 */
public class InMemoryNotificationRepository implements NotificationRepository{

    /**
     * Stores notifications by their unique ID.
     * Key: notificationId, Value: Notification object.
     */
    private final Map<String, Notification> notificationsById = new ConcurrentHashMap<>();

    /**
     * Maps recipient IDs to sets of notification IDs.
     * Example: "recipientID123" -> { "notif1", "notif2" }
     */
    private final Map<String, Set<String>> notificationsByRecipient = new HashMap<>();

    @Override
    public synchronized void addNotification(@NonNull Notification notification) {
        String notificationId = notification.getId();
        if(notificationsById.containsKey(notificationId)){return;}
        notificationsById.put(notificationId, notification);

        String recipientId = notification.getRecipientId();
        notificationsByRecipient
                .computeIfAbsent(recipientId, k-> new HashSet<>())
                .add(notificationId);

    }

    @Override
    public List<Notification> getNotificationsForRecipient(String recipientId) {
        Set<String> notifIds = notificationsByRecipient.getOrDefault(recipientId, Collections.emptySet());
        List<Notification> result = new ArrayList<>();
        for (String id : notifIds) {
            Notification n = notificationsById.get(id);
            if (n != null) {
                result.add(n);
            }
        }
        return result;
    }

    @Override
    public List<Notification> getPendingNotifications() {
        List<Notification> result = new ArrayList<>();
        for (Notification n : notificationsById.values()) {
            if (!n.isSent()) {
                result.add(n);
            }
        }
        return result;
    }

    @Override
    public void markAsSent(@NonNull String notificationId) {
        Notification notification = notificationsById.get(notificationId);
        notification.setSent(true);

    }

    @Override
    public synchronized void deleteNotification(@NonNull String notificationId) {
        Notification notification = notificationsById.remove(notificationId);
        // rep - {'notid', ''}
        Set<String> notificationIds = notificationsByRecipient.getOrDefault(notification.getRecipientId(), Collections.emptySet());
        if(notificationIds != null){
            notificationIds.remove(notificationId);
            if(notificationIds.isEmpty()){
                notificationsByRecipient.remove(notification.getRecipientId());
            }
        }

    }
}
