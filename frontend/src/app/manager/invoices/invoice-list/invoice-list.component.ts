import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { InvoiceService, Invoice } from '../../../core/services/invoice.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({ selector: 'app-invoice-list', templateUrl: './invoice-list.component.html' })
export class InvoiceListComponent implements OnInit, OnDestroy {
  displayedColumns = ['invoiceNumber', 'consultant', 'client', 'amountHT', 'amountTTC', 'status', 'dueDate', 'actions'];
  dataSource = new MatTableDataSource<Invoice>();
  loading = true;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  private destroy$ = new Subject<void>();

  constructor(private invoiceService: InvoiceService, private snack: MatSnackBar, private router: Router) {}

  ngOnInit(): void { this.load(); }

  ngAfterViewInit(): void { this.dataSource.paginator = this.paginator; this.dataSource.sort = this.sort; }

  load(): void {
    this.invoiceService.getAll().pipe(takeUntil(this.destroy$)).subscribe({
      next: d => { this.dataSource.data = d.content; this.loading = false; },
      error: () => this.loading = false
    });
  }

  send(id: number): void {
    this.invoiceService.send(id).pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.snack.open('Facture envoyée', 'OK', { duration: 3000 }); this.load();
    });
  }

  markPaid(id: number): void {
    const paidDate = new Date().toISOString().split('T')[0];
    this.invoiceService.markPaid(id, paidDate).pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.snack.open('Facture marquée payée', 'OK', { duration: 3000 }); this.load();
    });
  }

  viewDetail(id: number): void { this.router.navigate(['/manager/invoices', id]); }
  applyFilter(e: Event): void { this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLowerCase(); }
  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
