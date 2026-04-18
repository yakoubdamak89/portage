import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MissionService } from '../../../core/services/mission.service';
import { ConsultantService, Consultant } from '../../../core/services/consultant.service';
import { ClientService, Client } from '../../../core/services/client.service';

@Component({
  standalone: false,
  selector: 'app-mission-detail',
  templateUrl: './mission-detail.component.html',
  styleUrls: ['./mission-detail.component.scss']
})
export class MissionDetailComponent implements OnInit {
  form: FormGroup;
  id: number | null = null;
  loading = false;
  consultants: Consultant[] = [];
  clients: Client[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private missionService: MissionService,
    private consultantService: ConsultantService,
    private clientService: ClientService
  ) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      consultantId: ['', Validators.required],
      clientId: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      dailyRate: [0, [Validators.required, Validators.min(0)]],
      estimatedDays: [0],
      status: ['ACTIVE']
    });
  }

  ngOnInit(): void {
    this.consultantService.getAll().subscribe(d => this.consultants = d.content);
    this.clientService.getAll().subscribe(d => this.clients = d.content);
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && idParam !== 'new') {
      this.id = +idParam;
      this.missionService.getById(this.id).subscribe(m => this.form.patchValue(m));
    }
  }

  save(): void {
    if (this.form.invalid) return;
    const obs = this.id
      ? this.missionService.update(this.id, this.form.value)
      : this.missionService.create(this.form.value);
    obs.subscribe(() => this.router.navigate(['/manager/missions']));
  }
}
