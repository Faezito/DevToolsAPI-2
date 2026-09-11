import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  private _loading = signal(false);
  private inicioLoading = 0;
  private requisicoesAtivas = 0;

  readonly loading = this._loading.asReadonly();

  iniciar(): void {
    this.requisicoesAtivas++;

    if (this.requisicoesAtivas === 1) {
      this.inicioLoading = Date.now();
      this._loading.set(true);
    }
  }

  finalizar(): void {
    this.requisicoesAtivas--;

    if (this.requisicoesAtivas > 0) {
      return;
    }

    const tempoDecorrido = Date.now() - this.inicioLoading;
    const tempoRestante = 300 - tempoDecorrido;

    if (tempoRestante > 0) {
      setTimeout(() => {
        if (this.requisicoesAtivas === 0) {
          this._loading.set(false);
        }
      }, tempoRestante);
    } else {
      this._loading.set(false);
    }
  }
}