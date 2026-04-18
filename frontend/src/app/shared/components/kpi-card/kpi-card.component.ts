import { Component, Input } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-kpi-card',
  template: `
    <mat-card class="kpi-card">
      <mat-card-content>
        <div class="kpi-icon" [style.background-color]="color + '18'">
          <mat-icon [style.color]="color">{{icon}}</mat-icon>
        </div>
        <div class="kpi-body">
          <span class="kpi-value">{{value}}</span>
          <span class="kpi-label">{{label}}</span>
        </div>
      </mat-card-content>
    </mat-card>
  `,
  styles: [`
    .kpi-card {
      cursor: default;
      border-radius: 12px !important;
      box-shadow: 0 1px 3px rgba(0,0,0,.06), 0 4px 16px rgba(0,0,0,.06) !important;
      border: 1px solid rgba(0,0,0,.06) !important;
      transition: box-shadow 0.2s ease, transform 0.2s ease;
    }
    .kpi-card:hover {
      box-shadow: 0 4px 12px rgba(0,0,0,.1), 0 8px 24px rgba(0,0,0,.08) !important;
      transform: translateY(-1px);
    }
    mat-card-content {
      display: flex;
      align-items: center;
      gap: 18px;
      padding: 22px 20px !important;
    }
    .kpi-icon {
      width: 52px;
      height: 52px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }
    .kpi-icon mat-icon {
      font-size: 26px;
      width: 26px;
      height: 26px;
    }
    .kpi-body {
      display: flex;
      flex-direction: column;
      min-width: 0;
    }
    .kpi-value {
      font-size: 26px;
      font-weight: 700;
      color: #1a2332;
      line-height: 1.2;
      letter-spacing: -0.5px;
    }
    .kpi-label {
      font-size: 12.5px;
      color: #6b7280;
      margin-top: 3px;
      font-weight: 500;
    }
  `]
})
export class KpiCardComponent {
  @Input() label = '';
  @Input() value: string | number = '';
  @Input() icon = 'info';
  @Input() color = '#1d4ed8';
}
