import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { DashboardService, ConsultantDashboardStats } from '../../core/services/dashboard.service';

@Component({ standalone: false, selector: 'app-consultant-dashboard', templateUrl: './dashboard.component.html' })
export class ConsultantDashboardComponent implements OnInit, OnDestroy {
  stats: ConsultantDashboardStats = {
    currentMission: null,
    declaredDaysThisMonth: 0,
    monthlyRevenue: 0,
    walletBalance: 0,
    recentActivity: [],
    pendingTasks: []
  };
  loading = true;
  private destroy$ = new Subject<void>();

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getConsultantStats().pipe(takeUntil(this.destroy$)).subscribe({
      next: (s) => { this.stats = s; this.loading = false; },
      error: () => this.loading = false
    });
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }

  formatCurrency(v: number): string {
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(v);
  }
}
