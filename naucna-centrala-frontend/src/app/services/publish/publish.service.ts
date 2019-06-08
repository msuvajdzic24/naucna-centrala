import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PublishService {

  constructor(
    private httpClient: HttpClient
  ) { }

  startProcess() {
    let headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.httpClient.get('/api/publish/start', {headers: headers, responseType: 'text'});
  }

  getTasks(): Observable<any>  {
    return this.httpClient.get<any>('/api/publish/getTasks');
  }
  
  chooseJournal(taskId: string, obj): Observable<any> {
    return this.httpClient.post<any>('/api/publish/chooseJournal/' + taskId, obj);
  }

  payMembershipFee(taskId: string, obj) {
    return this.httpClient.post('/api/publish/payMembershipFee/' + taskId, obj);
  }

  submitArticle(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/submitArticle/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  getArticle(taskId: string): Observable<any> {
    return this.httpClient.get<any>('/api/publish/getArticle/' + taskId);
  }

  getJournal(taskId: string): Observable<any> {
    return this.httpClient.get<any>('/api/publish/getJournal/' + taskId);
  }

  downloadArticle(articleId) {
    return this.httpClient.get('/api/publish/download/' + articleId, {responseType: 'blob'});
  }

  decision(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/decision/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  getAuthor(taskId: string): Observable<any> {
    return this.httpClient.get<any>('/api/publish/getAuthor/' + taskId);
  }
  
  getComment(taskId: string) {
    let headers = new HttpHeaders();
    return this.httpClient.get('/api/publish/getComment/' + taskId, {headers: headers, responseType: 'text'})
  }

  resubmitArticle(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/resubmitArticle/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  getReviewers(taskId: string): Observable<any> {
    return this.httpClient.get<any>('/api/publish/getReviewers/' + taskId);
  }

  chooseReviewers(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/chooseReviewers/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  review(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/review/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  chooseNewReviewer(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/chooseNewReviewer/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  getReviewsForEditor(taskId: string): Observable<any> {
    return this.httpClient.get<any>('/api/publish/getReviewsEditor/' + taskId);
  }

  getAuthorMessage(taskId: string) {
    let headers = new HttpHeaders();
    return this.httpClient.get('/api/publish/getAuthorMessage/' + taskId, {headers: headers, responseType: 'text'});
  }

  analyzeReviews(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/analyzeReviews/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  getReviewsForAuthor(taskId: string): Observable<any> {
    return this.httpClient.get<any>('/api/publish/getReviewsAuthor/' + taskId);
  }

  submitCorrection(taskId: string, obj) {
    let headers = new HttpHeaders();
    return this.httpClient.post('/api/publish/submitCorrection/' + taskId, obj, {headers: headers, responseType: 'text'});
  }

  isFirstCylce(taskId: string): Observable<any> {
    return this.httpClient.get<any>('/api/publish/isFirstCycle/' + taskId);
  }

}
