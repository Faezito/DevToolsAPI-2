import { Component, OnInit, signal } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DevUpdateService } from '../../../services/devupdate.services';
import { DevUpdate } from '../../../models/devupdate.model';
import { DatePipe } from '@angular/common';
import { ModalGenerico } from '../../../components/modal-generico/modal-generico';

@Component({
  selector: 'app-updates-list',
  imports: [DatePipe],
  templateUrl: './updates-list.html',
  styleUrl: './updates-list.css',
})
export class UpdatesList implements OnInit {
  protected updates = signal<DevUpdate[]>([]);

  constructor(private devUpdateService: DevUpdateService, private modalService: NgbModal) { }

  ngOnInit(): void {
      this.devUpdateService.listar().subscribe({
        next: (updates) => {
          console.log(updates);
          this.updates.set(
              updates.sort((a, b) =>
                new Date(b.dataAtualizacao).getTime() -
                new Date(a.dataAtualizacao).getTime()
              )
          );
        },
        error: (error) => {
          console.error('Erro ao consultar API:', error);
        }
    });
  }

  abrirUpdate(update: DevUpdate): void {
    const modalRef = this.modalService.open(ModalGenerico, {
      size: 'xl',
      centered: true,
    });

    modalRef.componentInstance.props = {
      titulo: `${update.titulo} - ${update.sistema}`,
      texto: update.texto,
    };
  }

}