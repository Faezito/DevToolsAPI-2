import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { LoadingService } from "../services/loading.service";
import { catchError, finalize, throwError } from "rxjs";
import { GlobalErrorService } from "../services/global-error.service";

export const apiKeyInterceptor: HttpInterceptorFn = (req, next) => {
    const loadingService = inject(LoadingService);
    const globalErrorService = inject(GlobalErrorService);
    
    loadingService.iniciar();
    
    const authorization = req.headers.get('Authorization');

    if (authorization?.startsWith('Bearer ')) {
        return next(req).pipe(

            catchError(error => {
                globalErrorService.tratar(error);

                return throwError(() => error);
            }),

            finalize(() => loadingService.finalizar())
        );
    }

    const apiKey = window.__env.FRONTEND_API_KEY;

    const request = req.clone({
        setHeaders: {
            'X-API-KEY': apiKey || '',
        },
    });
    return next(request).pipe(

    catchError(error => {
        globalErrorService.tratar(error);

        return throwError(() => error);
        }),

    finalize(() => loadingService.finalizar())
    );
}