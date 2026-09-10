import { Component } from '@angular/core';
import { Menu } from '../../menu/menu/menu';
import Swal from "sweetalert2";
import { SideMenu } from '../../../components/side-menu/side-menu';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [Menu, SideMenu, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
}