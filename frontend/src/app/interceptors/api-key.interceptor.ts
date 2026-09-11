import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { LoadingService } from "../services/loading.service";
import { finalize } from "rxjs";

export const apiKeyInterceptor: HttpInterceptorFn = (req, next) => {
    const loadingService = inject(LoadingService);
    
    loadingService.iniciar();
    
    const apiKey = window.__env.FRONTEND_API_KEY;
    const request = req.clone({
        setHeaders: {
            'X-API-KEY': apiKey || '',
        },
    });
    return next(request).pipe(
        finalize(() => loadingService.finalizar())
    );
}