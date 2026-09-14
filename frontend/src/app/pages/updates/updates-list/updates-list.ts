import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DevUpdateService } from '../../../services/devupdate.services';
import { DevUpdate } from '../../../models/devupdate.model';
import { DatePipe, Location } from '@angular/common';
import { ModalGenerico } from '../../../components/modal-generico/modal-generico';
import { Tabela, Coluna } from '../../../components/tabela-generica/tabela-generica';
import { Filtro, FiltroDinamico } from '../../../components/filtro-dinamico/filtro-dinamico';
import { Menu, TopoMenu } from '../../../components/topo-menu/topo-menu';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-updates-list',
  imports: [DatePipe, Tabela, FiltroDinamico, TopoMenu, RouterLink],
  providers: [DatePipe],
  templateUrl: './updates-list.html',
  styleUrl: './updates-list.css',
})

export class UpdatesList implements OnInit {
  protected updates = signal<DevUpdate[]>([]);
  
  @ViewChild(FiltroDinamico)
  filtroDinamico!: FiltroDinamico<DevUpdate>;

  constructor(
    private devUpdateService: DevUpdateService,
    private modalService: NgbModal,
    private dtp: DatePipe,
    private location: Location,
    private router: Router
  ) { }

  colunas: Coluna<DevUpdate>[] = [
    {campo: 'id', titulo: 'Id'},
    {campo: 'titulo', titulo: 'Título'},
    {campo: 'sistema', titulo: 'Sistema'},
    {campo: 'dataAtualizacao', titulo: 'Data', classe: 'text-center', formato: 'data'},
    {campo: 'id', titulo: '', classe: 'text-center', acoes: ['editar', 'excluir']},
  ];

  filtros: Filtro<DevUpdate>[] = [
    {
      campo: 'titulo',
      titulo: 'Título',
      tipo: 'text'
    },
    {
      campo: 'sistemaId',
      titulo: 'Sistema',
      tipo: 'select',
      opcoes: [
        { valor: 2, texto: 'BOPE' }
      ]
    },
    {
      campo: 'dataAtualizacao',
      titulo: 'Data',
      tipo: 'date'
    }
  ];

  menu: Menu = {
    titulo: 'Atualizações',
    subtitulo: 'Lista de atualizações do sistema',
    btnCadastrar: true,
    btnVoltar: true
  };

  cadastrar(): void {
    this.router.navigate(['/cadastro']);
  }

  voltar(): void {
    this.location.back();
  }

  excluir(item: DevUpdate): void {
    Swal.fire({
        title: 'Excluir atualização?',
        text: `A atualização "${item.titulo}" será excluída.`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Excluir',
        cancelButtonText: 'Cancelar',
        reverseButtons: true
      }).then((resultado) => {

        if (!resultado.isConfirmed) {
          return;
        }

        this.devUpdateService.excluir(item.id).subscribe({
          next: () => {
            this.lista = this.lista.filter(x => x.id !== item.id);
            this.updates.set(this.lista);

            Swal.fire({
              title: 'Excluída!',
              text: 'A atualização foi excluída com sucesso.',
              icon: 'success',
              confirmButtonText: 'OK'
            });
          },
          error: (erro) => {
            console.error('Erro ao excluir atualização:', erro);

            Swal.fire({
              title: 'Erro',
              text: 'Não foi possível excluir a atualização.',
              icon: 'error',
              confirmButtonText: 'OK'
            });
          }
        });
      });
  }

  editar(item: DevUpdate): void {
    this.router.navigate(['/edicao', item.id]);
  }

  aplicarFiltros(filtros: Record<string, unknown>): void {
    const sistemaId = filtros['sistemaId'];
    const titulo = filtros['titulo'];
    const data = filtros['dataAtualizacao'];

    if (typeof sistemaId !== 'number' || sistemaId == 0) {
      this.filtroDinamico.marcarErro('sistemaId');
      return;
    }

    this.devUpdateService.listar(sistemaId).subscribe({
      next: (updates) => {
        const ordenados = updates.sort((a, b) =>
          new Date(b.dataAtualizacao).getTime() -
          new Date(a.dataAtualizacao).getTime()
        );
        
        let resultado = ordenados;
        if (typeof titulo === 'string' && titulo.trim()) {
            const termo = titulo.trim().toLowerCase();
            resultado = ordenados.filter(update =>
              update.titulo.toLowerCase().includes(termo)
            );
        }

        if (typeof data === 'string' && data) {
          resultado = resultado.filter(update =>
            update.dataAtualizacao.startsWith(data as string)
          );
        }

        this.lista = resultado;
        this.updates.set(resultado);
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