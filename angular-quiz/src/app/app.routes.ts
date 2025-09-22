import { Routes } from '@angular/router';
import { UsersListComponent } from './users/users-list.component';
import { UserDetailsComponent } from './users/user-details.component';

export const routes: Routes = [
  { path: '', component: UsersListComponent },
  { path: 'users/:id', component: UserDetailsComponent },
  { path: '**', redirectTo: '' }
];
