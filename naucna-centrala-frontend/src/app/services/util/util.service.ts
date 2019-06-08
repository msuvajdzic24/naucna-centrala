import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { RoleGuardService } from '../role-guard/role-guard.service';
import { Pager } from './Pager';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor() { }

  getUsername():string {
    const item = localStorage.getItem('user');
    if (!item) {
        return null;
    }
    let jwt: JwtHelperService = new JwtHelperService();
    const username = jwt.decodeToken(item).sub;
    return username;
  }

  getRole():string {
    const item = localStorage.getItem('user');
    if (!item) {
        return null;
    }
    let jwt: JwtHelperService = new JwtHelperService();
    const role = jwt.decodeToken(item).roles;
    return role;
  }

  getPager(pager: Pager) {
    if (pager.totalPages <= 7) {
      pager.firstPage = 1;
      pager.lastPage = pager.totalPages;
    } else {
      if (pager.currentPage <= 4) {
        pager.firstPage = 1;
        pager.lastPage = 7;
      } else if (pager.currentPage + 3 > pager.totalPages) {
        pager.firstPage = pager.totalPages - 6;
        pager.lastPage = pager.totalPages;
      } else {
        pager.firstPage = pager.currentPage - 3;
        pager.lastPage = pager.currentPage + 3;
      }
    }
    pager.pages = Array(pager.lastPage - pager.firstPage + 1).fill(0).map((e,i) => pager.firstPage + i);
    return pager;
}




}
