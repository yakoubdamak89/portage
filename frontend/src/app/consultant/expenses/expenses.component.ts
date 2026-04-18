import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ExpenseService, ExpenseReport } from '../../core/services/expense.service';

@Component({ standalone: false, selector: 'app-expenses', templateUrl: './expenses.component.html' })
export class ExpensesComponent implements OnInit, OnDestroy {
  expenses: ExpenseReport[] = [];
  displayedColumns = ['title', 'totalAmount', 'status', 'submittedAt', 'actions'];
  loading = true;
  private destroy$ = new Subject<void>();

  constructor(private expenseService: ExpenseService, private snack: MatSnackBar) {}

  ngOnInit(): void { this.loadExpenses(); }

  loadExpenses(): void {
    this.loading = true;
    this.expenseService.getMyExpenses().pipe(takeUntil(this.destroy$)).subscribe({
      next: (data) => { this.expenses = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  submit(id: number): void {
    this.expenseService.submit(id).pipe(takeUntil(this.destroy$)).subscribe({
      next: () => { this.snack.open('Note de frais soumise', 'OK', { duration: 3000 }); this.loadExpenses(); },
      error: () => this.snack.open('Erreur lors de la soumission', 'OK', { duration: 3000 })
    });
  }

  formatCurrency(v: number): string {
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(v);
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
