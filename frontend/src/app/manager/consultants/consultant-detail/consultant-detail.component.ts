import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ConsultantService, Consultant } from '../../../core/services/consultant.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({ standalone: false, selector: 'app-consultant-detail', templateUrl: './consultant-detail.component.html' })
export class ConsultantDetailComponent implements OnInit, OnDestroy {
  consultant: Consultant | null = null;
  form!: FormGroup;
  loading = true;
  saving = false;
  private destroy$ = new Subject<void>();

  constructor(private route: ActivatedRoute, private consultantService: ConsultantService, private fb: FormBuilder, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      firstName: ['', Validators.required], lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]], phone: [''],
      status: ['', Validators.required], dailyRate: [0, [Validators.required, Validators.min(0)]]
    });
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.consultantService.getById(id).pipe(takeUntil(this.destroy$)).subscribe({
      next: (c) => { this.consultant = c; this.form.patchValue(c); this.loading = false; },
      error: () => this.loading = false
    });
  }

  save(): void {
    if (this.form.invalid || !this.consultant) return;
    this.saving = true;
    this.consultantService.update(this.consultant.id, this.form.value).pipe(takeUntil(this.destroy$)).subscribe({
      next: (c) => { this.consultant = c; this.saving = false; this.snackBar.open('Consultant mis à jour', 'OK', { duration: 3000 }); },
      error: () => this.saving = false
    });
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
