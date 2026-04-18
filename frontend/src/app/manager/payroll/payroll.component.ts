import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { PayslipService, Payslip } from '../../core/services/payslip.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({ selector: 'app-payroll', templateUrl: './payroll.component.html' })
export class PayrollComponent implements OnInit, OnDestroy {
  displayedColumns = ['consultant', 'period', 'grossSalary', 'netSalary', 'status', 'actions'];
  dataSource = new MatTableDataSource<Payslip>();
  loading = true;
  private destroy$ = new Subject<void>();

  constructor(private payslipService: PayslipService, private snack: MatSnackBar) {}

  ngOnInit(): void {
    this.payslipService.getAll().pipe(takeUntil(this.destroy$)).subscribe({
      next: d => { this.dataSource.data = d.content; this.loading = false; },
      error: () => this.loading = false
    });
  }

  generate(consultantId: number, month: number, year: number): void {
    this.payslipService.generate(consultantId, month, year).pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.snack.open('Fiche de paie générée', 'OK', { duration: 3000 });
      this.ngOnInit();
    });
  }

  download(id: number): void { this.snack.open('Téléchargement en cours...', 'OK', { duration: 2000 }); }
  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
