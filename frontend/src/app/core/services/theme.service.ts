import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly STORAGE_KEY = 'portage_glass_mode';
  private glassModeSubject = new BehaviorSubject<boolean>(this.loadFromStorage());

  isGlassMode$ = this.glassModeSubject.asObservable();

  get isGlassMode(): boolean {
    return this.glassModeSubject.value;
  }

  toggleGlassMode(): void {
    const next = !this.glassModeSubject.value;
    this.glassModeSubject.next(next);
    localStorage.setItem(this.STORAGE_KEY, String(next));
  }

  private loadFromStorage(): boolean {
    return localStorage.getItem(this.STORAGE_KEY) === 'true';
  }
}
