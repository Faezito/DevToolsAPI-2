import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface LoginRequest {
  login: string;
  senha: string;
}

export interface LoginResponse {
  token: string;
  usuario: any;
}

@Injectable({
  providedIn: 'root',
})

export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/acesso';
  private readonly tokenKey = 'devtools_token';

  constructor(private http: HttpClient){}

  login(req: LoginRequest): Observable<LoginResponse>{
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/Login`,
      req
    );
  }

  salvarToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  obterToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  tokenExpirado(): boolean {
    const token = this.obterToken();

    if(!token){
      return true;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));

      if(!payload.exp){
        return true;
      }

      const agora = Math.floor(Date.now() / 1000);

      return payload.exp < agora;

    } catch {
      return true;
    }
  }

  removerToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  estaAutenticado(): boolean {
    const token = this.obterToken() !== null;

    if(!token) return false;

    if(this.tokenExpirado()){
      this.removerToken();
      return false;
    }

    return true;
  }

  logout(): void {
    this.removerToken();
  }
}
