import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { PayslipService, Payslip } from '../../core/services/payslip.service';

@Component({ selector: 'app-payslips', templateUrl: './payslips.component.html' })
export class PayslipsComponent implements OnInit, OnDestroy {
  payslips: Payslip[] = [];
  displayedColumns = ['period', 'grossSalary', 'netSalary', 'generatedAt', 'actions'];
  loading = true;
  private destroy$ = new Subject<void>();

  constructor(private payslipService: PayslipService, private snack: MatSnackBar) {}

  ngOnInit(): void { this.loadPayslips(); }

  loadPayslips(): void {
    this.loading = true;
    this.payslipService.getMyPayslips().pipe(takeUntil(this.destroy$)).subscribe({
      next: (data) => { this.payslips = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  download(id: number): void {
    this.payslipService.download(id).pipe(takeUntil(this.destroy$)).subscribe({
      next: (blob) => {
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `bulletin-paie-${id}.pdf`;
        a.click();
        URL.revokeObjectURL(url);
      },
      error: () => this.snack.open('Erreur lors du téléchargement', 'OK', { duration: 3000 })
    });
  }

  monthLabel(month: number, year: number): string {
    return new Date(year, month - 1, 1).toLocaleString('fr-FR', { month: 'long', year: 'numeric' });
  }

  formatCurrency(v: number): string {
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(v);
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
