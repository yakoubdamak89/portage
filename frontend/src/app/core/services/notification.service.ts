import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface Notification {
  id: number;
  type: string;
  message: string;
  read: boolean;
  createdAt: string;
  link?: string;
}

@Injectable({ providedIn: 'root' })
export class NotificationService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getAll(): Observable<Notification[]> {
    return this.get<Notification[]>('/notifications');
  }

  markRead(id: number): Observable<void> {
    return this.patch<void>(`/notifications/${id}/read`, {});
  }

  markAllRead(): Observable<void> {
    return this.patch<void>('/notifications/read-all', {});
  }

  getUnreadCount(): Observable<number> {
    return this.get<number>('/notifications/unread-count');
  }
}
