import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(
    private httpClient: HttpClient
  ) { }

  startProcess(): Observable<any> {
    return this.httpClient.get<any>('/api/registration/start');
  }

  register(taskId: string, form) {
    let headers = new HttpHeaders();
    return this.httpClient.post('api/registration/post/' + taskId, form, {headers: headers, responseType: 'text'});
  }

  confirmEmail(processId: string) {
    let headers = new HttpHeaders();
    return this.httpClient.get('api/registration/confirmEmail/' + processId, {headers: headers, responseType: 'text'});
  }


}
