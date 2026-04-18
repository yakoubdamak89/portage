import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/auth/auth.guard';
import { RoleGuard } from './core/auth/role.guard';

const routes: Routes = [
  { path: '', redirectTo: '/auth/login', pathMatch: 'full' },
  { path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
  { path: 'manager', loadChildren: () => import('./manager/manager.module').then(m => m.ManagerModule), canActivate: [AuthGuard, RoleGuard], data: { roles: ['ADMIN', 'GESTIONNAIRE'] } },
  { path: 'consultant', loadChildren: () => import('./consultant/consultant.module').then(m => m.ConsultantModule), canActivate: [AuthGuard, RoleGuard], data: { roles: ['SALARIE_PORTE', 'ADMIN'] } },
  { path: '**', redirectTo: '/auth/login' }
];

@NgModule({ imports: [RouterModule.forRoot(routes)], exports: [RouterModule] })
export class AppRoutingModule {}
