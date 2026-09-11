import { Component, OnInit, signal } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DevUpdateService } from '../../../services/devupdate.services';
import { DevUpdate } from '../../../models/devupdate.model';
import { DatePipe } from '@angular/common';
import { ModalGenerico } from '../../../components/modal-generico/modal-generico';
import { Tabela, Coluna } from '../../../components/tabela-generica/tabela-generica';
import { Filtro, FiltroDinamico } from '../../../components/filtro-dinamico/filtro-dinamico';

@Component({
  selector: 'app-updates-list',
  imports: [DatePipe, Tabela, FiltroDinamico],
  providers: [DatePipe],
  templateUrl: './updates-list.html',
  styleUrl: './updates-list.css',
})

export class UpdatesList implements OnInit {
  protected updates = signal<DevUpdate[]>([]);

  constructor(
    private devUpdateService: DevUpdateService,
    private modalService: NgbModal,
    private dtp: DatePipe) { }

  colunas: Coluna<DevUpdate>[] = [
    {campo: 'titulo', titulo: 'Título'},
    {campo: 'sistema', titulo: 'Sistema'},
    {campo: 'dataAtualizacao', titulo: 'Data', classe: 'text-center', formato: 'data'},
  ];

  filtros: Filtro<DevUpdate>[] = [
    {
      campo: 'titulo',
      titulo: 'Título',
      tipo: 'texto'
    },
    {
      campo: 'sistemaId',
      titulo: 'Sistema',
      tipo: 'select',
      opcoes: [
        { valor: 2, texto: 'BOPE' }
      ]
    }
  ];

  aplicarFiltros(filtros: Record<string, unknown>): void {
    const sistemaId = filtros['sistemaId'];

    if (typeof sistemaId !== 'number') {
      return;
    }

    this.devUpdateService.listar(sistemaId).subscribe({
      next: (updates) => {
        const ordenados = updates.sort((a, b) =>
          new Date(b.dataAtualizacao).getTime() -
          new Date(a.dataAtualizacao).getTime()
        );

        this.lista = ordenados;
        this.updates.set(ordenados);
      },
      error: (error) => {
        console.error('Erro ao consultar API:', error);
      }
    });
  }

  private lista: DevUpdate[] = [];

  ngOnInit(): void {
      this.devUpdateService.listar(2).subscribe({
        next: (updates) => {
            const ordenados = updates.sort((a, b) =>
                new Date(b.dataAtualizacao).getTime() -
                new Date(a.dataAtualizacao).getTime()
            );

            this.lista = ordenados;
            this.updates.set(ordenados);
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
      data: this.dtp.transform(update.dataAtualizacao, 'dd/MM/yyyy')
    };
  }
}