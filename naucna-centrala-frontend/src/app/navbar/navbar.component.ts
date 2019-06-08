import { Component, OnInit } from '@angular/core';
import { UtilService } from '../services/util/util.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  role: string;
  username: string;

  constructor(
    private utilService: UtilService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private toastrService: ToastrService
  ) { }

  ngOnInit() {
    this.username = this.utilService.getUsername();
    this.role = this.utilService.getRole();
  }

  logout() {
    this.authenticationService.logout().subscribe(
      result => {
        if (result) {
          localStorage.removeItem('user');
          this.router.navigate(['/login']);
        }
      },
      error => {
        console.log(error);
        this.toastrService.error(error.error);
      }
  )
  }

}
