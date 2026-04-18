import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AuthService } from '../../core/auth/auth.service';
import { ThemeService } from '../../core/services/theme.service';

@Component({ standalone: false, selector: 'app-manager-layout', templateUrl: './manager-layout.component.html', styleUrls: ['./manager-layout.component.scss'] })
export class ManagerLayoutComponent implements OnInit, OnDestroy {
  sidenavOpen = true;
  isGlassMode = false;
  private destroy$ = new Subject<void>();

  navItems = [
    { label: 'Tableau de bord', icon: 'dashboard', route: '/manager/dashboard' },
    { label: 'Consultants', icon: 'people', route: '/manager/consultants' },
    { label: 'Clients', icon: 'business', route: '/manager/clients' },
    { label: 'Missions', icon: 'work', route: '/manager/missions' },
    { label: "Comptes rendus d'activité", icon: 'event_note', route: '/manager/timesheets' },
    { label: 'Factures', icon: 'receipt', route: '/manager/invoices' },
    { label: 'Paiements', icon: 'payment', route: '/manager/payments' },
    { label: 'Gestion paie', icon: 'account_balance_wallet', route: '/manager/payroll' }
  ];

  constructor(public authService: AuthService, public themeService: ThemeService) {}

  ngOnInit(): void {
    this.themeService.isGlassMode$.pipe(takeUntil(this.destroy$)).subscribe(v => this.isGlassMode = v);
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }

  get currentUser() { return this.authService.getCurrentUser(); }
  logout() { this.authService.logout(); }
}

