import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorService {

    private exibirErro(titulo: string, mensagem: string): void {
    Swal.fire({
        icon: 'error',
        title: titulo,
        text: mensagem,
        confirmButtonText: 'OK'
    });
    }

    tratar(error: unknown): void {

    if (!(error instanceof HttpErrorResponse)) {
        console.error('Erro desconhecido:', error);
        return;
    }

    console.error('Erro HTTP:', {
        status: error.status,
        message: error.message,
        error: error.error
    });

    switch (error.status) {

        case 400:
        this.exibirErro(
            'Requisição inválida',
            '400: Os dados enviados não são válidos.'
        );
        break;

        case 401:
        this.exibirErro(
            'Não autenticado',
            '401: Sua sessão não é válida ou expirou.'
        );
        break;

        case 403:
        this.exibirErro(
            'Acesso negado',
            '403: Requisição inválida.'
        );
        break;

        case 404:
        this.exibirErro(
            'Não encontrado',
            '404: O recurso solicitado não foi encontrado.'
        );
        break;

        case 500:
        this.exibirErro(
            'Erro interno',
            '500: Ocorreu um erro no servidor. Tente novamente mais tarde.'
        );
        break;

        case 0:
        this.exibirErro(
            'Erro de conexão',
            'Não foi possível conectar ao servidor.'
        );
        break;

        default:
        this.exibirErro(
            'Erro',
            'Ocorreu um erro inesperado.'
        );
        break;
    }
    }

}