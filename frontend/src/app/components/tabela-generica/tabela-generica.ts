import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

export interface Coluna<T> {
  campo: keyof T;
  titulo: string;
  classe?: string;
  formato?: string;
  acao?: string;
}

@Component({
  selector: 'app-tabela-generica',
  imports: [DatePipe],
  providers: [DatePipe],
  templateUrl: './tabela-generica.html',
  styleUrl: './tabela-generica.css',
})

export class Tabela<T> {

  constructor(private dtp: DatePipe){}

  @Input()
  dados: T[] = [];

  @Input()
  colunas: Coluna<T>[] = [];

  @Output() detalhe = new EventEmitter<T>();
  @Output() excluir = new EventEmitter<T>();

  formatarValor(item: T, coluna: Coluna<T>): unknown {
    const valor = item[coluna.campo];

    if (coluna.formato === 'data') {
      return this.dtp.transform(valor as string | number | Date, 'dd/MM/yyyy');
    }

    return valor;
  }
}