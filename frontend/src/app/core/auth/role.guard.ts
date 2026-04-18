import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const requiredRoles: string[] = route.data['roles'] || [];
    const user = this.authService.getCurrentUser();
    if (!user) {
      this.router.navigate(['/auth/login']);
      return false;
    }
    if (requiredRoles.length === 0 || requiredRoles.includes(user.role)) {
      return true;
    }
    const role = user.role;
    if (role === 'SALARIE_PORTE') {
      this.router.navigate(['/consultant/dashboard']);
    } else {
      this.router.navigate(['/manager/dashboard']);
    }
    return false;
  }
}
