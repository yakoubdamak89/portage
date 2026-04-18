import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ClientService, Client } from '../../../core/services/client.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({ selector: 'app-client-detail', templateUrl: './client-detail.component.html' })
export class ClientDetailComponent implements OnInit, OnDestroy {
  client: Client | null = null;
  form!: FormGroup;
  loading = true;
  saving = false;
  private destroy$ = new Subject<void>();

  constructor(private route: ActivatedRoute, private clientService: ClientService, private fb: FormBuilder, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required], siret: [''], address: [''], city: [''], postalCode: [''],
      contactName: [''], contactEmail: ['', Validators.email], contactPhone: [''], status: ['ACTIF']
    });
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.clientService.getById(id).pipe(takeUntil(this.destroy$)).subscribe({ next: c => { this.client = c; this.form.patchValue(c); this.loading = false; }, error: () => this.loading = false });
  }

  save(): void {
    if (this.form.invalid || !this.client) return;
    this.saving = true;
    this.clientService.update(this.client.id, this.form.value).pipe(takeUntil(this.destroy$)).subscribe({ next: c => { this.client = c; this.saving = false; this.snackBar.open('Client mis à jour', 'OK', { duration: 3000 }); }, error: () => this.saving = false });
  }
  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
