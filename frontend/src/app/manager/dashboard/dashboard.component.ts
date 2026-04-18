import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { DashboardService, ManagerDashboardStats } from '../../core/services/dashboard.service';

@Component({ standalone: false, selector: 'app-manager-dashboard', templateUrl: './dashboard.component.html' })
export class ManagerDashboardComponent implements OnInit, OnDestroy {
  stats: ManagerDashboardStats = { activeConsultants: 0, activeMissions: 0, monthlyRevenue: 0, pendingInvoices: 0, recentInvoices: [], endingSoonMissions: [] };
  loading = true;
  invoiceColumns = ['invoiceNumber', 'consultantName', 'clientName', 'amountTTC', 'status', 'dueDate'];
  missionColumns = ['title', 'consultantName', 'clientName', 'endDate', 'status'];
  private destroy$ = new Subject<void>();

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getManagerStats().pipe(takeUntil(this.destroy$)).subscribe({
      next: (s) => { this.stats = s; this.loading = false; },
      error: () => this.loading = false
    });
  }
  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
  formatCurrency(v: number): string { return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(v); }
}
