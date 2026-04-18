import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { InvoiceService, Invoice } from '../../../core/services/invoice.service';
import { PaymentService } from '../../../core/services/payment.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({ selector: 'app-invoice-detail', templateUrl: './invoice-detail.component.html' })
export class InvoiceDetailComponent implements OnInit, OnDestroy {
  invoice: Invoice | null = null;
  payments: any[] = [];
  paymentForm: FormGroup;
  loading = true;
  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private invoiceService: InvoiceService,
    private paymentService: PaymentService,
    private snack: MatSnackBar,
    private fb: FormBuilder
  ) {
    this.paymentForm = this.fb.group({
      amount: [null, [Validators.required, Validators.min(0.01)]],
      paymentDate: [new Date(), Validators.required],
      reference: ['']
    });
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.invoiceService.getById(id).pipe(takeUntil(this.destroy$)).subscribe({
      next: inv => { this.invoice = inv; this.loading = false; },
      error: () => this.loading = false
    });
    this.paymentService.getByInvoice(id).pipe(takeUntil(this.destroy$)).subscribe(p => this.payments = p);
  }

  recordPayment(): void {
    if (this.paymentForm.invalid || !this.invoice) return;
    this.paymentService.create({ invoiceId: this.invoice.id, ...this.paymentForm.value })
      .pipe(takeUntil(this.destroy$)).subscribe(() => {
        this.snack.open('Paiement enregistré', 'OK', { duration: 3000 });
        this.paymentForm.reset({ paymentDate: new Date() });
        this.ngOnInit();
      });
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
