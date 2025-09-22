import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { UserService, ApiUser, UsersPage } from '../core/services/user.service';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatPaginatorModule],
  template: `
    <div class="container">
      <div class="center-row">
        <div class="card-grid" style="max-width: 900px; width: 100%;">
          <mat-card *ngFor="let u of users" style="cursor:pointer" (click)="open(u.id)" class="user-card" appearance="outlined">
            <img [src]="u.avatar" alt="avatar" style="width:100%; aspect-ratio: 1/1; object-fit: cover; border-top-left-radius: 4px; border-top-right-radius: 4px;" />
            <mat-card-content style="padding:12px 16px;">
              <div style="font-weight:600;">{{ u.first_name }} {{ u.last_name }}</div>
              <div style="opacity:.8; font-size: 12px;">ID: {{ u.id }}</div>
            </mat-card-content>
          </mat-card>
        </div>
      </div>

      <div class="center-row" style="margin-top: 12px;">
        <mat-paginator [length]="length" [pageIndex]="page-1" [pageSize]="perPage"
                       (page)="change($event)" [hidePageSize]="true" [showFirstLastButtons]="true">
        </mat-paginator>
      </div>
    </div>
  `,
  styles: [`
    .user-card { transition: transform .18s ease, box-shadow .18s ease; }
    .user-card:hover { transform: translateY(-3px); box-shadow: 0 10px 30px rgba(0,0,0,.25) }
  `]
})
export class UsersListComponent implements OnInit {
  private usersSvc = inject(UserService);
  private router = inject(Router);

  users: ApiUser[] = [];
  page = 1;
  perPage = 6;
  length = 0;

  ngOnInit() { this.load(this.page); }

  change(ev: PageEvent) { this.load((ev.pageIndex ?? 0) + 1); }

  load(page: number) {
    this.usersSvc.getUsers(page).subscribe((res: UsersPage) => {
      this.page = res.page;
      this.perPage = res.per_page;
      this.length = res.total;
      this.users = res.data;
    });
  }
  open(id: number) { this.router.navigate(['/users', id]); }
}
