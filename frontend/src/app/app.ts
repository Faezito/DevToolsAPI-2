import { Component, OnInit, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { DevUpdate } from './models/devupdate.model';
import { SideMenu } from './components/side-menu/side-menu';
import { Loading } from './components/loading/loading';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    SideMenu,
    RouterLink,
    Loading
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})

export class App {

  protected readonly title = signal('devtools-web');
  protected update?: DevUpdate;
  }