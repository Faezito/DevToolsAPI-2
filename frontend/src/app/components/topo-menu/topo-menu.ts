import { Component, EventEmitter, Input, Output } from '@angular/core';

export interface Menu {
  titulo: string;
  subtitulo?: string;
  btnVoltar?: boolean;
  btnCadastrar?: boolean;
}

@Component({
  selector: 'app-topo-menu',
  imports: [],
  templateUrl: './topo-menu.html',
  styleUrl: './topo-menu.css',
})

export class TopoMenu {
  @Input()
  menu: Menu = {
    titulo: ''
  };

  @Output()
  cadastrar = new EventEmitter<void>();

  @Output()
  voltar = new EventEmitter<void>();
}
