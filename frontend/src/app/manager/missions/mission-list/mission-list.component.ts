import { Component, OnInit } from '@angular/core';
import { MissionService, Mission } from '../../../core/services/mission.service';

@Component({
  selector: 'app-mission-list',
  templateUrl: './mission-list.component.html',
  styleUrls: ['./mission-list.component.scss']
})
export class MissionListComponent implements OnInit {
  missions: Mission[] = [];
  displayedColumns = ['title', 'consultant', 'client', 'startDate', 'endDate', 'dailyRate', 'status', 'actions'];
  loading = false;

  constructor(private missionService: MissionService) {}

  ngOnInit(): void {
    this.loading = true;
    this.missionService.getAll().subscribe({
      next: (data) => { this.missions = data.content; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }
}
