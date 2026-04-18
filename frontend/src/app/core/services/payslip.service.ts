import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface Payslip {
  id: number;
  consultantId: number;
  consultantName?: string;
  month: number;
  year: number;
  grossSalary: number;
  netSalary: number;
  charges: number;
  documentUrl?: string;
  generatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class PayslipService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getMyPayslips(): Observable<Payslip[]> {
    return this.get<Payslip[]>('/payslips/me');
  }

  getById(id: number): Observable<Payslip> {
    return this.get<Payslip>(`/payslips/${id}`);
  }

  getAll(page = 0, size = 10): Observable<{ content: Payslip[]; totalElements: number }> {
    return this.get<{ content: Payslip[]; totalElements: number }>('/payslips', { page, size });
  }

  generate(consultantId: number, month: number, year: number): Observable<Payslip> {
    return this.post<Payslip>('/payslips/generate', { consultantId, month, year });
  }

  download(id: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/payslips/${id}/download`, { responseType: 'blob' });
  }
}
