import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface TimesheetEntry {
  date: string;
  hoursWorked: number;
  notes?: string;
}

export interface Timesheet {
  id: number;
  consultantId: number;
  consultantName?: string;
  missionId: number;
  missionTitle?: string;
  month: number;
  year: number;
  status: 'BROUILLON' | 'SOUMIS' | 'VALIDE' | 'REJETE';
  entries: TimesheetEntry[];
  totalDays: number;
  submittedAt?: string;
  validatedAt?: string;
  rejectionReason?: string;
}

@Injectable({ providedIn: 'root' })
export class TimesheetService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getMyTimesheets(): Observable<Timesheet[]> {
    return this.get<Timesheet[]>('/timesheets/me');
  }

  getByConsultant(consultantId: number): Observable<Timesheet[]> {
    return this.get<Timesheet[]>(`/timesheets/consultant/${consultantId}`);
  }

  getByMonthYear(month: number, year: number): Observable<Timesheet[]> {
    return this.get<Timesheet[]>('/timesheets', { month, year });
  }

  getById(id: number): Observable<Timesheet> {
    return this.get<Timesheet>(`/timesheets/${id}`);
  }

  create(timesheet: Partial<Timesheet>): Observable<Timesheet> {
    return this.post<Timesheet>('/timesheets', timesheet);
  }

  update(id: number, timesheet: Partial<Timesheet>): Observable<Timesheet> {
    return this.put<Timesheet>(`/timesheets/${id}`, timesheet);
  }

  submit(id: number): Observable<Timesheet> {
    return this.patch<Timesheet>(`/timesheets/${id}/submit`, {});
  }

  validate(id: number): Observable<Timesheet> {
    return this.patch<Timesheet>(`/timesheets/${id}/validate`, {});
  }

  reject(id: number, reason: string): Observable<Timesheet> {
    return this.patch<Timesheet>(`/timesheets/${id}/reject`, { reason });
  }

  getMonthlyDays(): Observable<number> {
    const now = new Date();
    return this.get<number>('/timesheets/me/days-this-month', { month: now.getMonth() + 1, year: now.getFullYear() });
  }
}
