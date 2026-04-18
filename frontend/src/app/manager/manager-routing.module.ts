import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManagerLayoutComponent } from './manager-layout/manager-layout.component';
import { ManagerDashboardComponent as DashboardComponent } from './dashboard/dashboard.component';
import { ConsultantListComponent } from './consultants/consultant-list/consultant-list.component';
import { ConsultantDetailComponent } from './consultants/consultant-detail/consultant-detail.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import { ClientDetailComponent } from './clients/client-detail/client-detail.component';
import { MissionListComponent } from './missions/mission-list/mission-list.component';
import { MissionDetailComponent } from './missions/mission-detail/mission-detail.component';
import { TimesheetsComponent } from './timesheets/timesheets.component';
import { InvoiceListComponent } from './invoices/invoice-list/invoice-list.component';
import { InvoiceDetailComponent } from './invoices/invoice-detail/invoice-detail.component';
import { PaymentsComponent } from './payments/payments.component';
import { PayrollComponent } from './payroll/payroll.component';

const routes: Routes = [
  {
    path: '',
    component: ManagerLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'consultants', component: ConsultantListComponent },
      { path: 'consultants/:id', component: ConsultantDetailComponent },
      { path: 'clients', component: ClientListComponent },
      { path: 'clients/:id', component: ClientDetailComponent },
      { path: 'missions', component: MissionListComponent },
      { path: 'missions/:id', component: MissionDetailComponent },
      { path: 'timesheets', component: TimesheetsComponent },
      { path: 'invoices', component: InvoiceListComponent },
      { path: 'invoices/:id', component: InvoiceDetailComponent },
      { path: 'payments', component: PaymentsComponent },
      { path: 'payroll', component: PayrollComponent },
    ]
  }
];

@NgModule({ imports: [RouterModule.forChild(routes)], exports: [RouterModule] })
export class ManagerRoutingModule {}
