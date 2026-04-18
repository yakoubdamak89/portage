import { Component, Input } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-kpi-card',
  template: `
    <mat-card class="kpi-card">
      <mat-card-content>
        <div class="kpi-icon" [style.background-color]="color + '20'">
          <mat-icon [style.color]="color">{{icon}}</mat-icon>
        </div>
        <div class="kpi-info">
          <span class="kpi-value">{{value}}</span>
          <span class="kpi-label">{{label}}</span>
        </div>
      </mat-card-content>
    </mat-card>
  `,
  styles: [`
    .kpi-card { cursor: default; }
    mat-card-content { display: flex; align-items: center; gap: 16px; padding: 20px !important; }
    .kpi-icon { width: 56px; height: 56px; border-radius: 12px; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }
    .kpi-icon mat-icon { font-size: 28px; width: 28px; height: 28px; }
    .kpi-info { display: flex; flex-direction: column; }
    .kpi-value { font-size: 28px; font-weight: 600; color: #212121; line-height: 1.2; }
    .kpi-label { font-size: 13px; color: #757575; margin-top: 4px; }
  `]
})
export class KpiCardComponent {
  @Input() label = '';
  @Input() value: string | number = '';
  @Input() icon = 'info';
  @Input() color = '#1565C0';
}
