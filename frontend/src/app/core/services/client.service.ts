import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';

export interface Client {
  id: number;
  name: string;
  siret?: string;
  address?: string;
  city?: string;
  postalCode?: string;
  country?: string;
  contactName?: string;
  contactEmail?: string;
  contactPhone?: string;
  status: 'ACTIF' | 'INACTIF';
  createdAt?: string;
}

export interface ClientPage {
  content: Client[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class ClientService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  getAll(page = 0, size = 10, search = ''): Observable<ClientPage> {
    return this.get<ClientPage>('/clients', { page, size, search });
  }

  getById(id: number): Observable<Client> {
    return this.get<Client>(`/clients/${id}`);
  }

  create(client: Partial<Client>): Observable<Client> {
    return this.post<Client>('/clients', client);
  }

  update(id: number, client: Partial<Client>): Observable<Client> {
    return this.put<Client>(`/clients/${id}`, client);
  }

  remove(id: number): Observable<void> {
    return this.delete<void>(`/clients/${id}`);
  }
}
