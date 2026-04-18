import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface Payment {
  id: number;
  invoiceId: number;
  invoiceNumber?: string;
  consultantId: number;
  consultantName?: string;
  amount: number;
  paymentDate: string;
  method: 'VIREMENT' | 'CHEQUE' | 'PRELEVEMENT';
  reference?: string;
  notes?: string;
}

export interface PaymentPage {
  content: Payment[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class PaymentService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getAll(page = 0, size = 10): Observable<PaymentPage> {
    return this.get<PaymentPage>('/payments', { page, size });
  }

  getById(id: number): Observable<Payment> {
    return this.get<Payment>(`/payments/${id}`);
  }

  getByInvoice(invoiceId: number): Observable<Payment[]> {
    return this.get<Payment[]>(`/payments/invoice/${invoiceId}`);
  }

  create(payment: Partial<Payment>): Observable<Payment> {
    return this.post<Payment>('/payments', payment);
  }

  getMonthlyRevenue(): Observable<number> {
    const now = new Date();
    return this.get<number>('/payments/revenue', { month: now.getMonth() + 1, year: now.getFullYear() });
  }
}
