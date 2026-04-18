import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface Mission {
  id: number;
  title: string;
  consultantId: number;
  consultantName?: string;
  clientId: number;
  clientName?: string;
  startDate: string;
  endDate: string;
  status: 'EN_COURS' | 'TERMINEE' | 'SUSPENDUE' | 'A_VENIR';
  dailyRate: number;
  description?: string;
  createdAt?: string;
}

export interface MissionPage {
  content: Mission[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class MissionService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getAll(page = 0, size = 10): Observable<MissionPage> {
    return this.get<MissionPage>('/missions', { page, size });
  }

  getById(id: number): Observable<Mission> {
    return this.get<Mission>(`/missions/${id}`);
  }

  getByConsultant(consultantId: number): Observable<Mission[]> {
    return this.get<Mission[]>(`/missions/consultant/${consultantId}`);
  }

  create(mission: Partial<Mission>): Observable<Mission> {
    return this.post<Mission>('/missions', mission);
  }

  update(id: number, mission: Partial<Mission>): Observable<Mission> {
    return this.put<Mission>(`/missions/${id}`, mission);
  }

  getActiveCount(): Observable<number> {
    return this.get<number>('/missions/count/active');
  }

  getEndingSoon(): Observable<Mission[]> {
    return this.get<Mission[]>('/missions/ending-soon');
  }
}
