import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged, filter, map, switchMap, tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { UserService, ApiUser } from '../../../core/services/user.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatIconModule, MatCardModule, NgIf],
  template: `
    <div class="toolbar-search">
    <mat-form-field appearance="outline" class="search-field">
        <mat-label>Instant search by ID</mat-label>
        <input matInput type="text" [formControl]="query" placeholder="e.g. 2" />
        <mat-icon matSuffix>search</mat-icon>
    </mat-form-field>

    <!-- Result card (hit) -->
    <div class="search-result" *ngIf="result">
        <mat-card style="cursor:pointer" (click)="goTo(result!.id)">
        <mat-card-header>
            <div mat-card-avatar [style.background]="'center / cover url(' + result!.avatar + ')'"></div>
            <mat-card-title>{{ result!.first_name }} {{ result!.last_name }}</mat-card-title>
            <mat-card-subtitle>#{{ result!.id }}</mat-card-subtitle>
        </mat-card-header>
        </mat-card>
    </div>

    <!-- No-hit message -->
    <div class="search-result nohit" *ngIf="showNoHit">
        <mat-card><mat-card-content>No user found</mat-card-content></mat-card>
    </div>
    </div>
  `
})
export class HeaderComponent {
  private users = inject(UserService);
  private router = inject(Router);
  query = new FormControl('', { nonNullable: true });
  result: ApiUser | null = null;

  // guard to prevent 404 noise
  readonly MIN_ID = 1;
  readonly MAX_ID = 12;
  showNoHit = false;

  constructor() {
    this.query.valueChanges.pipe(
      debounceTime(250),
      distinctUntilChanged(),
      map(v => v.trim()),
      tap(() => { this.result = null; this.showNoHit = false; }),
      filter(v => v.length > 0),
      filter(v => /^\d+$/.test(v)),                         // only digits
      tap(v => {
        const id = +v;
        if (id < this.MIN_ID || id > this.MAX_ID) {
          // out of known range → don't call API, just show message
          this.showNoHit = true;
        }
      }),
      filter(v => {
        const id = +v;
        return id >= this.MIN_ID && id <= this.MAX_ID;     // only in-range hits call API
      }),
      switchMap(v => this.users.getUserById(+v).pipe(
        catchError(() => of(null))                         // extra safety
      ))
    ).subscribe(user => {
      this.result = user;
      this.showNoHit = user === null;                      // in-range but not found (unlikely) → message
    });
  }

  goTo(id: number) {
    this.result = null;
    this.query.setValue('');
    this.router.navigate(['/users', id]);
  }
}
