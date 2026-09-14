import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);
  const token = authService.obterToken();
  const router = inject(Router);

  if(!token){
    return next(req);
  }

  const request = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(request).pipe(
    catchError(erro => {
      if(erro.status === 401){
        authService.logout();
        router.navigate(['/login']);
      }

      return throwError(() => erro);
    })
  );
};
