import { Routes } from '@angular/router';
import { UpdatesList } from './pages/updates/updates-list/updates-list';
import { Home } from './pages/home/home/home';

export const routes: Routes = [
    {
        path: '',
        component: Home
    },
    {
        path: 'updates',
        component: UpdatesList,
    }
];
