import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DevUpdateService } from './services/devupdate.services';
import { DevUpdate } from './models/devupdate.model';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  protected readonly title = signal('devtools-web');
  protected update?: DevUpdate;

  constructor(private devUpdateService: DevUpdateService) {
    this.devUpdateService.obter(11).subscribe({
      next: (update) => {
        this.update = update;
      },
      error: (error) => {
        console.error('Erro ao consultar API:', error);
      }
    });
  }
}