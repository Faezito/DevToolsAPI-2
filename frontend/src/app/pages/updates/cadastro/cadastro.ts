import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Menu, TopoMenu } from '../../../components/topo-menu/topo-menu';
import { Router, RouterLink } from '@angular/router';
import { DatePipe, Location } from '@angular/common';
import { QuillModule } from 'ngx-quill';
import { DevUpdateService } from '../../../services/devupdate.services';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [ReactiveFormsModule, TopoMenu, RouterLink, QuillModule],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css',
})

export class Cadastro {
  constructor(
    private location: Location,
    private router: Router,
    private devUpdateService: DevUpdateService
  ){}

  menu: Menu = {
    titulo: 'Adicionar nota de atualização',
    btnVoltar: true
  }

  cadastrar(): void {
    this.router.navigate(['/cadastro']);
  }

  voltar(): void {
    this.location.back();
  }

  quillConfig = {
    toolbar: [
      ['bold', 'italic', 'underline'],
      [{ 'size': ['small', false, 'large', 'huge'] }],
      [{ 'align': [] }],
      [{ 'list': 'ordered' }, { 'list': 'bullet' }],
      ['link']
    ]
  };

  formulario = new FormGroup({

    titulo: new FormControl('',{
      validators: [
        Validators.required,
        Validators.minLength(10)
      ]
    }),

    texto: new FormControl('', {
      validators: [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(10000)
      ]
    }),

    sistemaId: new FormControl(0, {
      validators: [
        Validators.required,
        Validators.min(1)
      ]
    }),

    dataAtualizacao: new FormControl('', {
      validators: [
        Validators.required
      ]
    })
  });

  get titulo() {
    return this.formulario.controls.titulo;
  }

  get sistemaId() {
    return this.formulario.controls.sistemaId;
  }

  get texto() {
    return this.formulario.controls.texto;
  }

  get dataAtualizacao() {
    return this.formulario.controls.dataAtualizacao;
  }

  mensagemErro(controle: any): string {

      if (controle.hasError('required')) {
        return 'Este campo é obrigatório.';
      }

      if (controle.hasError('minlength')) {
        return `Este campo deve ter pelo menos ${controle.getError('minlength').requiredLength} caracteres.`;
      }

      if (controle.hasError('maxlength')) {
        return `Este campo deve ter no máximo ${controle.getError('maxlength').requiredLength} caracteres.`;
      }

      if (controle.hasError('min')) {
        return 'O valor selecionado é inválido.';
      }

      return '';
  }

  salvar(): void {

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const dados = {
      ...this.formulario.value,
      dataAtualizacao: `${this.formulario.value.dataAtualizacao}T00:00:00`
    };

    console.log(dados);

    this.devUpdateService.inserir(dados as any)
      .subscribe({
        next: () => {
            Swal.fire({
                title: 'Sucesso!',
                text: `Cadastro efetuado com sucesso! Deseja permanecer na página?`,
                icon: 'success',
                showCancelButton: true,
                confirmButtonText: 'Sair',
                cancelButtonText: 'Permanecer',
                reverseButtons: true
              }).then((resultado) => {
                if(resultado.isConfirmed){
                    this.voltar();
                  }
              });
            },
            error: (erro) => {
              console.error('Erro ao cadastrar atualização:', erro);
            }
        });
  }
}
