import { Component } from '@angular/core';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-consultant-layout',
  templateUrl: './consultant-layout.component.html',
  styleUrls: ['./consultant-layout.component.scss']
})
export class ConsultantLayoutComponent {
  sidenavOpen = true;
  navItems = [
    { label: 'Tableau de bord', icon: 'dashboard', route: '/consultant/dashboard' },
    { label: "Comptes rendus d'activité", icon: 'event_note', route: '/consultant/activity' },
    { label: 'Notes de frais', icon: 'receipt_long', route: '/consultant/expenses' },
    { label: 'Bulletins de paie', icon: 'description', route: '/consultant/payslips' },
    { label: 'Documents', icon: 'folder', route: '/consultant/documents' },
    { label: 'Mon compte épargne', icon: 'account_balance_wallet', route: '/consultant/wallet' }
  ];

  constructor(public authService: AuthService) {}

  get currentUser() { return this.authService.getCurrentUser(); }
  logout() { this.authService.logout(); }
}
