import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { ManagerRoutingModule } from './manager-routing.module';
import { ManagerLayoutComponent } from './manager-layout/manager-layout.component';
import { ManagerDashboardComponent } from './dashboard/dashboard.component';
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
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@NgModule({
  declarations: [
    ManagerLayoutComponent,
    ManagerDashboardComponent,
    ConsultantListComponent,
    ConsultantDetailComponent,
    ClientListComponent,
    ClientDetailComponent,
    MissionListComponent,
    MissionDetailComponent,
    TimesheetsComponent,
    InvoiceListComponent,
    InvoiceDetailComponent,
    PaymentsComponent,
    PayrollComponent,
  ],
  imports: [SharedModule, ManagerRoutingModule, MatSlideToggleModule]
})
export class ManagerModule {}
