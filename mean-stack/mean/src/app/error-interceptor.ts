import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private snackBar: MatSnackBar) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        const config = new MatSnackBarConfig();
        config.duration = 10000;
        config.panelClass = ['custom-class'];

        const errMsg = error.error.message || 'An unknown error has occurred';
        this.snackBar.open('Error: ' + errMsg, 'Ok', config);

        return throwError(error);
      })
    );
  }
}
