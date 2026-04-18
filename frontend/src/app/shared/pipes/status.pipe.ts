import { Pipe, PipeTransform } from '@angular/core';

const STATUS_LABELS: Record<string, string> = {
  ACTIF: 'Actif',
  INACTIF: 'Inactif',
  EN_ATTENTE: 'En attente',
  EN_COURS: 'En cours',
  TERMINEE: 'Terminée',
  SUSPENDUE: 'Suspendue',
  A_VENIR: 'À venir',
  BROUILLON: 'Brouillon',
  SOUMIS: 'Soumis',
  VALIDE: 'Validé',
  REJETE: 'Rejeté',
  ENVOYEE: 'Envoyée',
  PAYEE: 'Payée',
  EN_RETARD: 'En retard',
  ANNULEE: 'Annulée',
  REMBOURSE: 'Remboursé'
};

@Pipe({ standalone: false, name: 'status' })
export class StatusPipe implements PipeTransform {
  transform(value: string): string {
    return STATUS_LABELS[value] || value;
  }
}
