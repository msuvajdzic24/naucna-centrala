import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post("api/login", {"username": username, "password": password}, {headers: this.headers, responseType: 'json', withCredentials: true});
  }

  logout() {
    return this.http.get("api/logoutUser", {headers: this.headers, responseType: 'text', withCredentials: true});
  }

  isLoggedIn() : boolean {
    const token = localStorage.getItem('user');

    if (!token) {
        return false;
    }
    return true;
  }
  
}
