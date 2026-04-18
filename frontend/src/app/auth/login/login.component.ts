import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({ standalone: false, selector: 'app-login', templateUrl: './login.component.html', styleUrls: ['./login.component.scss'] })
export class LoginComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  error = '';
  hidePassword = true;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      rememberMe: [false]
    });
    if (this.authService.isLoggedIn()) this.redirect();
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    this.loading = true;
    this.error = '';
    this.authService.login({ email: this.form.value.email, password: this.form.value.password }).subscribe({
      next: () => this.redirect(),
      error: (err) => { this.error = err.error?.message || 'Identifiants invalides'; this.loading = false; }
    });
  }

  private redirect(): void {
    const user = this.authService.getCurrentUser();
    const returnUrl = this.route.snapshot.queryParams['returnUrl'];
    if (returnUrl) { this.router.navigateByUrl(returnUrl); return; }
    if (user?.role === 'SALARIE_PORTE') this.router.navigate(['/consultant/dashboard']);
    else this.router.navigate(['/manager/dashboard']);
  }
}
