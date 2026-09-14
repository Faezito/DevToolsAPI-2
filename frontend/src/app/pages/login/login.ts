import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  login = '';
  senha = '';
  erro = '';
  carregando = false;

  constructor(private authService: AuthService, 
    private router: Router) {  }

  entrar(): void {
    this.erro = '';
    this.carregando = true;

    this.authService.login({
      login: this.login,
      senha: this.senha
    }).subscribe({
      next: (resposta) => {
        this.authService.salvarToken(resposta.token);
        this.router.navigate(['/']);
      },
      error: () => {
        this.erro = 'Credenciais inválidas';
        this.carregando = false;
      }
    });
  }
}
