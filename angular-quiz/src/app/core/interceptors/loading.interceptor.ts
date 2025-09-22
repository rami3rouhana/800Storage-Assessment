import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { LoadingService } from '../services/loading.service';

const MIN_VISIBLE_MS = 300;

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loading = inject(LoadingService);

  const startedAt = Date.now();
  queueMicrotask(() => loading.increment());

  return next(req).pipe(
    finalize(() => {
      const elapsed = Date.now() - startedAt;
      const wait = Math.max(0, MIN_VISIBLE_MS - elapsed);
      setTimeout(() => queueMicrotask(() => loading.decrement()), wait);
    })
  );
};
