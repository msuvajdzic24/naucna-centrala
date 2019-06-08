import { Injectable } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginGuardService {

  constructor(
    public auth: AuthenticationService, 
    public router: Router
  ) {}
    
  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {
        this.router.navigate(['/home']);
        return false;
    }
    return true;
  }
}
