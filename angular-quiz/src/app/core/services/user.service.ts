import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, shareReplay, catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

export interface ApiUser {
  id: number;
  email: string;
  first_name: string;
  last_name: string;
  avatar: string;
}
export interface UsersPage {
  page: number;
  per_page: number;
  total: number;
  total_pages: number;
  data: ApiUser[];
}

@Injectable({ providedIn: 'root' })
export class UserService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getUsers(page: number): Observable<UsersPage> {
    return this.http
      .get<UsersPage>(`${this.base}/users?page=${page}`)
      .pipe(shareReplay(1));
  }

  getUserById(id: number): Observable<ApiUser | null> {
    return this.http
      .get<{ data: ApiUser }>(`${this.base}/users/${id}`)
      .pipe(
        map(r => r?.data ?? null),
        catchError(() => of(null)),
        shareReplay(1)
      );
  }
}
