import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TimesheetService, Timesheet } from '../../core/services/timesheet.service';

@Component({ selector: 'app-activity', templateUrl: './activity.component.html' })
export class ActivityComponent implements OnInit, OnDestroy {
  timesheets: Timesheet[] = [];
  displayedColumns = ['period', 'mission', 'days', 'status', 'actions'];
  loading = true;
  submitting = false;
  selectedMonth = new Date().getMonth() + 1;
  selectedYear = new Date().getFullYear();
  months = [1,2,3,4,5,6,7,8,9,10,11,12];
  years: number[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private timesheetService: TimesheetService,
    private fb: FormBuilder,
    private snack: MatSnackBar
  ) {
    const current = new Date().getFullYear();
    for (let y = current - 2; y <= current + 1; y++) this.years.push(y);
  }

  ngOnInit(): void { this.loadTimesheets(); }

  loadTimesheets(): void {
    this.loading = true;
    this.timesheetService.getMyTimesheets().pipe(takeUntil(this.destroy$)).subscribe({
      next: (data) => { this.timesheets = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  submit(id: number): void {
    this.timesheetService.submit(id).pipe(takeUntil(this.destroy$)).subscribe({
      next: () => { this.snack.open('CRA soumis avec succès', 'OK', { duration: 3000 }); this.loadTimesheets(); },
      error: () => this.snack.open('Erreur lors de la soumission', 'OK', { duration: 3000 })
    });
  }

  monthLabel(month: number): string {
    return new Date(2000, month - 1, 1).toLocaleString('fr-FR', { month: 'long' });
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
