import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EditionService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient
  ) { }

  getEdition(id: number): Observable<any> {
    return this.http.get<any>("/api/edition/" + id);
  }

  getEditions(): Observable<any[]> {
    return this.http.get<any[]>("/api/editions", {headers: this.headers, responseType: 'json'});
  }

  buyEdition(id: number) {
    return this.http.get('/api/edition/' + id + "/buy", {headers: this.headers, responseType: 'text'});
  }

}
