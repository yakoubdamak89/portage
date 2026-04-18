import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ClientService, Client } from '../../../core/services/client.service';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({ selector: 'app-client-list', templateUrl: './client-list.component.html' })
export class ClientListComponent implements OnInit, OnDestroy {
  displayedColumns = ['name', 'siret', 'contactName', 'status', 'actions'];
  dataSource = new MatTableDataSource<Client>();
  searchControl = new FormControl('');
  loading = true;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  private destroy$ = new Subject<void>();

  constructor(private clientService: ClientService, private router: Router) {}

  ngOnInit(): void {
    this.clientService.getAll(0, 100).pipe(takeUntil(this.destroy$)).subscribe({ next: p => { this.dataSource.data = p.content; this.loading = false; }, error: () => this.loading = false });
    this.searchControl.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(v => this.dataSource.filter = (v||'').trim().toLowerCase());
  }
  ngAfterViewInit(): void { this.dataSource.paginator = this.paginator; this.dataSource.sort = this.sort; }
  viewDetail(id: number): void { this.router.navigate(['/manager/clients', id]); }
  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
