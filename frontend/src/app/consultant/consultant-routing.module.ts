import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultantLayoutComponent } from './consultant-layout/consultant-layout.component';
import { ConsultantDashboardComponent } from './dashboard/dashboard.component';
import { ActivityComponent } from './activity/activity.component';
import { ExpensesComponent } from './expenses/expenses.component';
import { PayslipsComponent } from './payslips/payslips.component';
import { DocumentsComponent } from './documents/documents.component';
import { WalletComponent } from './wallet/wallet.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultantLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: ConsultantDashboardComponent },
      { path: 'activity', component: ActivityComponent },
      { path: 'expenses', component: ExpensesComponent },
      { path: 'payslips', component: PayslipsComponent },
      { path: 'documents', component: DocumentsComponent },
      { path: 'wallet', component: WalletComponent }
    ]
  }
];

@NgModule({ imports: [RouterModule.forChild(routes)], exports: [RouterModule] })
export class ConsultantRoutingModule {}
