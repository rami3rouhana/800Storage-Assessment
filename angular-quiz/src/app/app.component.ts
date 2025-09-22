import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { HeaderComponent } from './shared/components/header/header.component';
import { LoadingService } from './core/services/loading.service';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatToolbarModule, MatIconModule, MatProgressBarModule, HeaderComponent, NgIf, AsyncPipe],
  template: `
    <mat-toolbar style="position: relative;">
      <span class="header-spacer"></span>
      <app-header></app-header>
            <mat-progress-bar
        *ngIf="loading.isLoading$ | async"
        mode="indeterminate"
        color="warn"
        style="position:absolute; left:0; right:0; bottom:0;">
      </mat-progress-bar>
    </mat-toolbar>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  loading = inject(LoadingService);
}
