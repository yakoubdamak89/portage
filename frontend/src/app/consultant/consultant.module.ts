import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { ConsultantRoutingModule } from './consultant-routing.module';
import { ConsultantLayoutComponent } from './consultant-layout/consultant-layout.component';
import { ConsultantDashboardComponent } from './dashboard/dashboard.component';
import { ActivityComponent } from './activity/activity.component';
import { ExpensesComponent } from './expenses/expenses.component';
import { PayslipsComponent } from './payslips/payslips.component';
import { DocumentsComponent } from './documents/documents.component';
import { WalletComponent } from './wallet/wallet.component';

@NgModule({
  declarations: [
    ConsultantLayoutComponent,
    ConsultantDashboardComponent,
    ActivityComponent,
    ExpensesComponent,
    PayslipsComponent,
    DocumentsComponent,
    WalletComponent
  ],
  imports: [SharedModule, ConsultantRoutingModule]
})
export class ConsultantModule {}
