import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

export interface Filtro<T> {
  campo: keyof T;
  titulo: string;
  tipo: 'text' | 'select' | 'date' | 'email';
  opcoes?: OpcaoFiltro[];
}

export interface OpcaoFiltro {
  valor: unknown;
  texto: string;
}

@Component({
  selector: 'filtro',
  imports: [FormsModule],
  templateUrl: './filtro-dinamico.html',
  styleUrl: './filtro-dinamico.css',
  standalone: true
})
export class FiltroDinamico<T> {

  @Input()
  filtros: Filtro<T>[] = [];

  valores: Partial<Record<keyof T, unknown>> = {};
  filtroErro: keyof T | null = null;

  @Output()
  filtrar = new EventEmitter<Partial<Record<keyof T, unknown>>>();

  aplicar(): void {
    this.filtrar.emit(this.valores);
  }
  
  marcarErro(campo: keyof T): void {
    this.filtroErro = campo;

    setTimeout(() => {
      this.filtroErro = null;
    }, 1000);
  }
}