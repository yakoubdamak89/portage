import { Component } from '@angular/core';
import { AuthService } from '../../core/auth/auth.service';

@Component({ selector: 'app-manager-layout', templateUrl: './manager-layout.component.html', styleUrls: ['./manager-layout.component.scss'] })
export class ManagerLayoutComponent {
  sidenavOpen = true;
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
  constructor(public authService: AuthService) {}
  get currentUser() { return this.authService.getCurrentUser(); }
  logout() { this.authService.logout(); }
}
