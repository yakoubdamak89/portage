import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Subject } from 'rxjs';
import { takeUntil, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { ConsultantService, Consultant } from '../../../core/services/consultant.service';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({ selector: 'app-consultant-list', templateUrl: './consultant-list.component.html' })
export class ConsultantListComponent implements OnInit, OnDestroy {
  displayedColumns = ['firstName', 'email', 'status', 'dailyRate', 'managerName', 'actions'];
  dataSource = new MatTableDataSource<Consultant>();
  searchControl = new FormControl('');
  loading = true;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  private destroy$ = new Subject<void>();

  constructor(private consultantService: ConsultantService, private router: Router) {}

  ngOnInit(): void {
    this.loadConsultants();
    this.searchControl.valueChanges.pipe(debounceTime(300), distinctUntilChanged(), takeUntil(this.destroy$))
      .subscribe(v => this.dataSource.filter = (v || '').trim().toLowerCase());
  }

  ngAfterViewInit(): void { this.dataSource.paginator = this.paginator; this.dataSource.sort = this.sort; }

  loadConsultants(): void {
    this.consultantService.getAll(0, 100).pipe(takeUntil(this.destroy$)).subscribe({
      next: (p) => { this.dataSource.data = p.content; this.loading = false; },
      error: () => this.loading = false
    });
  }

  viewDetail(id: number): void { this.router.navigate(['/manager/consultants', id]); }
  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
