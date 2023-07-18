import NotificationService from '@/shared/notification/notification-service';
import { inject } from '@vue/composition-api';

export function useNotification() {
  const notificationService: NotificationService = inject<NotificationService>('notificationService');
  return notificationService;
}
