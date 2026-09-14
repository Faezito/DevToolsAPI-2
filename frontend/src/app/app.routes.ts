import { Routes } from '@angular/router';
import { UpdatesList } from './pages/updates/updates-list/updates-list';
import { Home } from './pages/home/home/home';
import { Edicao } from './pages/updates/edicao/edicao';
import { Login } from './pages/login/login';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
    {
        path: 'login',
        component: Login
    },
    {
        path: '',
        component: Home,
        canActivate: [authGuard]
    },
    {
        path: 'updates',
        component: UpdatesList,
        canActivate: [authGuard]
    },
    {
        path: 'cadastro',
        loadComponent: () =>
            import('./pages/updates/cadastro/cadastro')
            .then(m => m.Cadastro),
        canActivate: [authGuard]
    },
    {
        path: 'edicao/:id',
        component: Edicao,
        canActivate: [authGuard]
    }
];
