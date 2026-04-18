import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface WalletMovement {
  id: number;
  type: 'CREDIT' | 'DEBIT';
  amount: number;
  description: string;
  date: string;
  balance: number;
}

export interface Wallet {
  id: number;
  consultantId: number;
  balance: number;
  movements: WalletMovement[];
}

export interface WalletSimulation {
  currentBalance: number;
  projectedRevenue: number;
  estimatedCharges: number;
  estimatedSalary: number;
  estimatedBalance: number;
}

@Injectable({ providedIn: 'root' })
export class WalletService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getMyWallet(): Observable<Wallet> {
    return this.get<Wallet>('/wallet/me');
  }

  getMovements(): Observable<WalletMovement[]> {
    return this.get<WalletMovement[]>('/wallet/me/movements');
  }

  simulate(params: { dailyRate: number; daysWorked: number }): Observable<WalletSimulation> {
    return this.post<WalletSimulation>('/wallet/simulate', params);
  }

  getBalance(): Observable<number> {
    return this.get<number>('/wallet/me/balance');
  }
}
