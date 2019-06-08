import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient
  ) { }

  getArticle(id: number): Observable<any> {
    return this.http.get<any>("/api/article/" + id);
  }

  getArticles(): Observable<any[]> {
    return this.http.get<any[]>("/api/articles", {headers: this.headers, responseType: 'json'});
  }

	buyArticle(id: number) {
    return this.http.get('/api/article/' + id + "/buy", {headers: this.headers, responseType: 'text'});
  }
}
