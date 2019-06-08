import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JournalService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient
  ) { }

  getJournal(id: number): Observable<any> {
    return this.http.get<any>("/api/journal/" + id);
  }

  getJournals(): Observable<any[]> {
    return this.http.get<any[]>("/api/journals", {headers: this.headers, responseType: 'json'});
  }

  payMembershipFee(id: number) {
    return this.http.get('/api/journal/' + id + "/payMembershipFee", {headers: this.headers, responseType: 'text'});
  }

  subscribe(id: number) {
    return this.http.get('/api/journal/' + id + "/subscribe", {headers: this.headers, responseType: 'text'});
  }

  unsubscribe(id: number) {
    return this.http.get('/api/journal/' + id + "/unsubcribe", {headers: this.headers, responseType: 'text'});
  }

}
