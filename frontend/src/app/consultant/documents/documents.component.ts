import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ApiService } from '../../core/services/api.service';

export interface ConsultantDocument {
  id: number;
  name: string;
  type: string;
  uploadedAt: string;
  url?: string;
}

@Component({ standalone: false, selector: 'app-documents', templateUrl: './documents.component.html' })
export class DocumentsComponent implements OnInit, OnDestroy {
  documents: ConsultantDocument[] = [];
  displayedColumns = ['name', 'type', 'uploadedAt', 'actions'];
  loading = true;
  private destroy$ = new Subject<void>();

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loading = true;
    this.apiService.get<ConsultantDocument[]>('/documents/me').pipe(takeUntil(this.destroy$)).subscribe({
      next: (data) => { this.documents = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
