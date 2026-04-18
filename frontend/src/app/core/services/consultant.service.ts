import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface Consultant {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
  status: 'ACTIF' | 'INACTIF' | 'EN_ATTENTE';
  dailyRate: number;
  managerId?: number;
  managerName?: string;
  createdAt?: string;
}

export interface ConsultantPage {
  content: Consultant[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class ConsultantService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getAll(page = 0, size = 10, search = ''): Observable<ConsultantPage> {
    return this.get<ConsultantPage>('/consultants', { page, size, search });
  }

  getById(id: number): Observable<Consultant> {
    return this.get<Consultant>(`/consultants/${id}`);
  }

  create(consultant: Partial<Consultant>): Observable<Consultant> {
    return this.post<Consultant>('/consultants', consultant);
  }

  update(id: number, consultant: Partial<Consultant>): Observable<Consultant> {
    return this.put<Consultant>(`/consultants/${id}`, consultant);
  }

  remove(id: number): Observable<void> {
    return this.delete<void>(`/consultants/${id}`);
  }

  getActiveCount(): Observable<number> {
    return this.get<number>('/consultants/count/active');
  }
}
