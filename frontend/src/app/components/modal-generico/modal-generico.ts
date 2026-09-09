import { Component, Input } from '@angular/core';

@Component({
  selector: 'modal-generico',
  imports: [],
  templateUrl: './modal-generico.html',
  styleUrl: './modal-generico.css',
})

export class ModalGenerico {
  @Input({ required: true })
  props!: ModalGenericoProps;
}

export interface ModalGenericoProps {
  titulo: string;
  texto: string;
}