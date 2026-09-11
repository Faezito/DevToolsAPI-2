import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'modal-generico',
  imports: [],
  templateUrl: './modal-generico.html',
  styleUrl: './modal-generico.css',
})

export class ModalGenerico {
  constructor(public activeModal: NgbActiveModal){}

  @Input({ required: true })
  props!: ModalGenericoProps;
}

export interface ModalGenericoProps {
  titulo: string;
  texto: string;
  data: string;
}