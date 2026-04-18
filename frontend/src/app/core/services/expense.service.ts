import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface ExpenseItem {
  id?: number;
  description: string;
  category: string;
  amount: number;
  date: string;
  receiptUrl?: string;
}

export interface ExpenseReport {
  id: number;
  consultantId: number;
  consultantName?: string;
  missionId?: number;
  missionTitle?: string;
  title: string;
  status: 'BROUILLON' | 'SOUMIS' | 'VALIDE' | 'REJETE' | 'REMBOURSE';
  totalAmount: number;
  items: ExpenseItem[];
  submittedAt?: string;
  validatedAt?: string;
  createdAt?: string;
}

@Injectable({ providedIn: 'root' })
export class ExpenseService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getMyExpenses(): Observable<ExpenseReport[]> {
    return this.get<ExpenseReport[]>('/expenses/me');
  }

  getById(id: number): Observable<ExpenseReport> {
    return this.get<ExpenseReport>(`/expenses/${id}`);
  }

  create(expense: Partial<ExpenseReport>): Observable<ExpenseReport> {
    return this.post<ExpenseReport>('/expenses', expense);
  }

  update(id: number, expense: Partial<ExpenseReport>): Observable<ExpenseReport> {
    return this.put<ExpenseReport>(`/expenses/${id}`, expense);
  }

  submit(id: number): Observable<ExpenseReport> {
    return this.patch<ExpenseReport>(`/expenses/${id}/submit`, {});
  }

  validate(id: number): Observable<ExpenseReport> {
    return this.patch<ExpenseReport>(`/expenses/${id}/validate`, {});
  }
}
