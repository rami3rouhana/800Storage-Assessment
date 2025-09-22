import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { UserService, ApiUser } from '../core/services/user.service';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [CommonModule, NgIf, MatCardModule, MatButtonModule],
  template: `
    <div class="container center-row">
      <mat-card style="max-width: 720px; width:100%;">
        <img *ngIf="user" [src]="user!.avatar" alt="avatar" style="width:100%; max-height: 420px; object-fit: cover;" />
        <mat-card-content *ngIf="user" style="padding:16px 20px;">
          <h2 style="margin: 0 0 8px 0;">{{ user.first_name }} {{ user.last_name }}</h2>
          <p style="opacity:.85; margin:0 0 4px 0;"><strong>ID:</strong> {{ user.id }}</p>
          <p style="opacity:.85; margin:0 0 16px 0;"><strong>Email:</strong> {{ user.email }}</p>
          <button mat-raised-button color="primary" (click)="back()">Back</button>
        </mat-card-content>
      </mat-card>
    </div>
  `
})
export class UserDetailsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private usersSvc = inject(UserService);

  user: ApiUser | null = null;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.router.navigateByUrl('/');
      return;
    }
    this.usersSvc.getUserById(id).subscribe(user => this.user = user);
  }

  back() { history.state?.navigationId ? history.back() : this.router.navigateByUrl('/'); }
}
