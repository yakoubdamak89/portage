import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { WalletService, Wallet, WalletMovement, WalletSimulation } from '../../core/services/wallet.service';

@Component({ standalone: false, selector: 'app-wallet', templateUrl: './wallet.component.html' })
export class WalletComponent implements OnInit, OnDestroy {
  wallet: Wallet | null = null;
  movements: WalletMovement[] = [];
  simulation: WalletSimulation | null = null;
  movementColumns = ['date', 'description', 'type', 'amount', 'balance'];
  loading = true;
  simulating = false;
  simulationForm: FormGroup;
  private destroy$ = new Subject<void>();

  constructor(private walletService: WalletService, private fb: FormBuilder) {
    this.simulationForm = this.fb.group({
      dailyRate: [null, [Validators.required, Validators.min(1)]],
      daysWorked: [null, [Validators.required, Validators.min(1), Validators.max(31)]]
    });
  }

  ngOnInit(): void {
    this.walletService.getMyWallet().pipe(takeUntil(this.destroy$)).subscribe({
      next: (w) => { this.wallet = w; this.movements = w.movements; this.loading = false; },
      error: () => this.loading = false
    });
  }

  simulate(): void {
    if (this.simulationForm.invalid) return;
    this.simulating = true;
    this.walletService.simulate(this.simulationForm.value).pipe(takeUntil(this.destroy$)).subscribe({
      next: (s) => { this.simulation = s; this.simulating = false; },
      error: () => this.simulating = false
    });
  }

  formatCurrency(v: number): string {
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(v);
  }

  ngOnDestroy(): void { this.destroy$.next(); this.destroy$.complete(); }
}
