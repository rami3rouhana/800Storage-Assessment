import { Injectable } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class LoadingService {
  private pending = new BehaviorSubject(0);
  readonly isLoading$ = this.pending.asObservable().pipe(map(v => v > 0));

  increment() { this.pending.next(this.pending.value + 1); }
  decrement() { this.pending.next(Math.max(0, this.pending.value - 1)); }
  reset() { this.pending.next(0); }
}
