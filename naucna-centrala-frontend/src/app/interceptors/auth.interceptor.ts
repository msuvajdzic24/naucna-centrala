import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { tap } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(
        private router: Router
    ) {}

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {

        const item = localStorage.getItem("user");
        const decodedItem = JSON.parse(item);

        req = req.clone({
            withCredentials: true
        });

        if (item) {
            const cloned = req.clone({
                headers: req.headers.set("X-Auth-Token", decodedItem.token)
            });

            let jwt: JwtHelperService = new JwtHelperService();

            const info = jwt.decodeToken(item);

            let expiration = info.expiring;
            
            let now = new Date();
            let exp = new Date(expiration);

            if (now > exp) {
                localStorage.removeItem('user');
                this.router.navigate(['/login']);
                return;
            }
            
            return next.handle(cloned).pipe(
                tap(event => {
                  if (event instanceof HttpResponse) {
                    //console.log(event);
                  }
                })
            );
        }
        else {
            return next.handle(req).pipe(
                tap(event => {
                  if (event instanceof HttpResponse) {
                    //let a: string = event.headers.get('Set-Cookie');
                    //console.log(a);
                  }
                })
            );
        }
    }

}