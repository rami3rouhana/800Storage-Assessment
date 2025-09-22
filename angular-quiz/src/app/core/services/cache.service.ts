import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class CacheService {
  private cache = new Map<string, HttpResponse<any>>();
  get(url: string) { return this.cache.get(url) || null; }
  set(url: string, response: HttpResponse<any>) { this.cache.set(url, response); }
  clear() { this.cache.clear(); }
}
