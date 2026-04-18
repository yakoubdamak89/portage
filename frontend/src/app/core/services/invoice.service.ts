import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface Invoice {
  id: number;
  invoiceNumber: string;
  consultantId: number;
  consultantName?: string;
  clientId: number;
  clientName?: string;
  missionId: number;
  amountHT: number;
  tva: number;
  amountTTC: number;
  status: 'BROUILLON' | 'ENVOYEE' | 'PAYEE' | 'EN_RETARD' | 'ANNULEE';
  issueDate: string;
  dueDate: string;
  paidDate?: string;
  createdAt?: string;
}

export interface InvoicePage {
  content: Invoice[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class InvoiceService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getAll(page = 0, size = 10): Observable<InvoicePage> {
    return this.get<InvoicePage>('/invoices', { page, size });
  }

  getById(id: number): Observable<Invoice> {
    return this.get<Invoice>(`/invoices/${id}`);
  }

  getRecent(limit = 5): Observable<Invoice[]> {
    return this.get<Invoice[]>('/invoices/recent', { limit });
  }

  getPendingCount(): Observable<number> {
    return this.get<number>('/invoices/count/pending');
  }

  create(invoice: Partial<Invoice>): Observable<Invoice> {
    return this.post<Invoice>('/invoices', invoice);
  }

  update(id: number, invoice: Partial<Invoice>): Observable<Invoice> {
    return this.put<Invoice>(`/invoices/${id}`, invoice);
  }

  send(id: number): Observable<Invoice> {
    return this.patch<Invoice>(`/invoices/${id}/send`, {});
  }

  markPaid(id: number, paidDate: string): Observable<Invoice> {
    return this.patch<Invoice>(`/invoices/${id}/pay`, { paidDate });
  }

  cancel(id: number): Observable<Invoice> {
    return this.patch<Invoice>(`/invoices/${id}/cancel`, {});
  }
}
