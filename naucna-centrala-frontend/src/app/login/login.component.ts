import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  error: string;

  constructor(
    private router: Router,
    private authenticateService: AuthenticationService,
    private toastrService: ToastrService
  ) { }

  ngOnInit() {
  }

  login() {
    this.authenticateService.login(this.username, this.password)
    .subscribe(
      result => {
        localStorage.setItem('user', JSON.stringify(result));
        this.router.navigate(['home']);
        this.toastrService.success("Successful login!");
      },
      error => {
        this.error = "Wrong username or password!";
        //console.log(error);
        //this.toastrService.error(error.error);
      }
    )
  }

}
