import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import { loadingInterceptor } from './app/core/interceptors/loading.interceptor';
import { cacheInterceptor } from './app/core/interceptors/cache.interceptor';
import { authInterceptor } from './app/core/interceptors/auth.interceptor';


bootstrapApplication(AppComponent, {
  providers: [
    provideAnimations(),
    provideHttpClient(
      withInterceptors([authInterceptor, loadingInterceptor, cacheInterceptor])
    ),
    provideRouter(routes)
  ]
}).catch(err => console.error(err));