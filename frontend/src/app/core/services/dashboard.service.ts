import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface ManagerDashboardStats {
  activeConsultants: number;
  activeMissions: number;
  monthlyRevenue: number;
  pendingInvoices: number;
  recentInvoices: any[];
  endingSoonMissions: any[];
}

export interface ConsultantDashboardStats {
  currentMission: string | null;
  declaredDaysThisMonth: number;
  monthlyRevenue: number;
  walletBalance: number;
  recentActivity: any[];
  pendingTasks: string[];
}

@Injectable({ providedIn: 'root' })
export class DashboardService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getManagerStats(): Observable<ManagerDashboardStats> {
    return this.get<ManagerDashboardStats>('/dashboard/manager');
  }

  getConsultantStats(): Observable<ConsultantDashboardStats> {
    return this.get<ConsultantDashboardStats>('/dashboard/consultant');
  }
}
