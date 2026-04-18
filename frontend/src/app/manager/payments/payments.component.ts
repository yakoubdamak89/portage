import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { PaymentService, Payment } from '../../core/services/payment.service';

@Component({ selector: 'app-payments', templateUrl: './payments.component.html' })
export class PaymentsComponent implements OnInit, OnDestroy {
  displayedColumns = ['invoiceNumber', 'client', 'amount', 'paymentDate', 'reference'];
  dataSource = new MatTableDataSource<Payment>();
  loading = true;
  private destroy$ = new Subject<void>();

  constructor(private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.paymentService.getAll().pipe(takeUntil(this.destroy$)).subscribe({
      next: d => { this.dataSource.data = d.content; this.loading = false; },
      error: () => this.loading = false
    });
  }

  applyFilter(e: Event): void { this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLowerCase(); }
  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
