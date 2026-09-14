import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DevUpdateService } from '../../../services/devupdate.services';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { timeout } from 'rxjs';
import { DevUpdate } from '../../../models/devupdate.model';
import Swal from 'sweetalert2';
import { QuillModule } from 'ngx-quill';
import { Menu, TopoMenu } from '../../../components/topo-menu/topo-menu';

@Component({
  selector: 'app-edicao',
  imports: [ReactiveFormsModule, TopoMenu, RouterLink, QuillModule],
  templateUrl: './edicao.html',
  styleUrl: './edicao.css',
})

export class Edicao implements OnInit {
  private id!: number;

  constructor(
    private devUpdateService: DevUpdateService,
    private route: ActivatedRoute,
    private router: Router
  ){}

  menu: Menu = {
    titulo: 'Editar nota de atualização',
    btnVoltar: true
  }

  form = new FormGroup({
    titulo: new FormControl('', {
      validators: [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100)
      ]
    }),

    sistemaId: new FormControl(0, {
      validators: [
        Validators.required,
        Validators.min(1)
      ]
    }),

    texto: new FormControl('', {
      validators: [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(10000)
      ]
    }),

    dataAtualizacao: new FormControl('', {
      validators: [
        Validators.required
      ]
    })
  })

  get titulo() {
    return this.form.controls.titulo;
  }

  get sistemaId() {
    return this.form.controls.sistemaId;
  }

  get texto() {
    return this.form.controls.texto;
  }

  get dataAtualizacao() {
    return this.form.controls.dataAtualizacao;
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

  salvar(): void {
    if(this.form.invalid){
      this.form.markAllAsTouched();
      return;
    }

    const dados = {
      id: this.id,
      ...this.form.value,
      dataAtualizacao: `${this.form.value.dataAtualizacao}T00:00:00`
    }

    console.log(dados);

    this.devUpdateService.atualizar(dados as DevUpdate)
      .subscribe({
        next: () => {
          Swal.fire({
            title: 'Atualização realizada!',
            text: 'A atualização foi editada com sucesso. Deseja voltar para a lista?',
            icon: 'success',
            showCancelButton: true,
            confirmButtonText: 'Voltar',
            cancelButtonText: 'Continuar editando',
            reverseButtons: true
          }).then((resultado) => {
            if (resultado.isConfirmed) {
              this.voltar();
            }
          });
        },
        error: (erro) => {
          console.error('Erro: ', erro);
        }
      })
  }

  voltar(): void {
    this.router.navigate(['/updates']);
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

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    if(!this.id){
      this.router.navigate(['/cadastro']);
      return;
    }

    this.devUpdateService.obter(this.id).subscribe({
      next: (update) => {
        console.log(update.texto);

        this.form.patchValue({
          titulo: update.titulo,
          sistemaId: update.sistemaId,
          texto: update.texto,
          dataAtualizacao: update.dataAtualizacao.substring(0, 10)
        })
      },
      error(err) {
        console.log('Erro: ', err)
      },
    })
  }
}
