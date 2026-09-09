import { HttpInterceptorFn } from "@angular/common/http";

export const apiKeyInterceptor: HttpInterceptorFn = (req, next) => {
    const apiKey = window.__env.FRONTEND_API_KEY;

    const request = req.clone({
        setHeaders: {
            'X-API-KEY': apiKey || '',
        },
    });
    return next(request);
}