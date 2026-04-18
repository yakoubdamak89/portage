import { Component, OnInit } from '@angular/core';
import { TimesheetService, Timesheet } from '../../../core/services/timesheet.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-timesheets',
  templateUrl: './timesheets.component.html',
  styleUrls: ['./timesheets.component.scss']
})
export class TimesheetsComponent implements OnInit {
  timesheets: Timesheet[] = [];
  displayedColumns = ['consultant', 'mission', 'period', 'days', 'status', 'actions'];
  loading = false;

  constructor(private timesheetService: TimesheetService, private snack: MatSnackBar) {}

  ngOnInit(): void {
    this.loading = true;
    const now = new Date();
    this.timesheetService.getByMonthYear(now.getMonth() + 1, now.getFullYear()).subscribe({
      next: (data) => { this.timesheets = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  validate(id: number): void {
    this.timesheetService.validate(id).subscribe(() => {
      this.snack.open('CRA validé', 'OK', { duration: 3000 });
      this.ngOnInit();
    });
  }

  reject(id: number): void {
    this.timesheetService.reject(id, 'À corriger').subscribe(() => {
      this.snack.open('CRA rejeté', 'OK', { duration: 3000 });
      this.ngOnInit();
    });
  }
}
