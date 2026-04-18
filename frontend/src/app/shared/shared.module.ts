import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';
import { MatTabsModule } from '@angular/material/tabs';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatBadgeModule } from '@angular/material/badge';
import { KpiCardComponent } from './components/kpi-card/kpi-card.component';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { StatusPipe } from './pipes/status.pipe';

const MATERIAL_MODULES = [
  MatCardModule, MatButtonModule, MatIconModule, MatTableModule, MatPaginatorModule,
  MatSortModule, MatInputModule, MatFormFieldModule, MatSelectModule, MatDialogModule,
  MatSnackBarModule, MatChipsModule, MatProgressSpinnerModule, MatTooltipModule,
  MatMenuModule, MatTabsModule, MatDatepickerModule, MatNativeDateModule, MatCheckboxModule, MatBadgeModule
];

@NgModule({
  declarations: [KpiCardComponent, ConfirmDialogComponent, StatusPipe],
  imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule, ...MATERIAL_MODULES],
  exports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule, KpiCardComponent, ConfirmDialogComponent, StatusPipe, ...MATERIAL_MODULES]
})
export class SharedModule {}
