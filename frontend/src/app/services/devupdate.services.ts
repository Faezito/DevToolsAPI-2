import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DevUpdate } from '../models/devupdate.model';

@Injectable({
  providedIn: 'root'
})
export class DevUpdateService {

  private readonly apiUrl = 'http://localhost:8080/updates';

  constructor(private http: HttpClient) {}

  obter(id: number): Observable<DevUpdate> {
    return this.http.get<DevUpdate>(
      `${this.apiUrl}/Obter/${id}`
    );
  }

  listar(sistemaId: number): Observable<DevUpdate[]> {
    return this.http.get<DevUpdate[]>(`${this.apiUrl}/Listar/${sistemaId}`);
  }
}