import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

import { AuthData } from './auth-data.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private userId: string;
  private isAuthenticated = false;
  private token: string;
  private tokenTimer: NodeJS.Timer;
  private authStatusListener = new Subject<boolean>();

  constructor(private http: HttpClient, private router: Router) {}

  getToken() {
    return this.token;
  }

  getIsAuth() {
    return this.isAuthenticated;
  }

  getAuthStatusListener() {
    return this.authStatusListener.asObservable();
  }

  getUserId() {
    return this.userId;
  }

  createUser(email: string, password: string) {
    const authData: AuthData = {
      email,
      password
    };

    this.http.post(environment.backendSignupUrl, authData)
      .subscribe(() => {
        this.router.navigate(['/']);
      }, error => {
        this.authStatusListener.next(false);
      });
  }

  login(email: string, password: string) {
    const authData: AuthData = {
      email,
      password
    };

    this.http.post<{token: string, expiresIn: number, userId: string}>(environment.backendLoginUrl, authData)
      .subscribe(response => {
        this.token = response.token;

        if (this.token) {
          this.userId = response.userId;
          this.isAuthenticated = true;
          this.authStatusListener.next(true);
          const expiresIn = response.expiresIn;

          this.setAuthTimer(expiresIn);

          const expirationDate = new Date(Date.now() + expiresIn * 1000);
          this.saveAuthData(this.token, expirationDate, this.userId);
        }
        this.router.navigate(['/']);
      }, error => {
        this.authStatusListener.next(false);
      });
  }

  logout() {
    this.userId = null;
    this.token = null;
    this.isAuthenticated = false;
    this.authStatusListener.next(false);
    clearTimeout(this.tokenTimer);
    this.clearAuthData();
    this.router.navigate(['/']);
  }

  autoAuthUser() {
    const authInformation = this.getAuthData();

    if (authInformation) {
      const tokenExpiration = new Date(authInformation.tokenExpiration);
      const now = new Date();
      const expiresIn = tokenExpiration.getTime() - now.getTime();

      if (expiresIn > 0) {
        this.userId = authInformation.userId;
        this.token = authInformation.token;
        this.isAuthenticated = true;
        this.setAuthTimer(expiresIn / 1000);
        this.authStatusListener.next(true);
      }
    }
  }

  // In seconds
  private setAuthTimer(expiresIn: number) {
    this.tokenTimer = setTimeout(() => {
      this.logout();
    }, expiresIn * 1000); // milliseconds
  }

  private saveAuthData(token: string, expirationDate: Date, userId: string) {
    localStorage.setItem('userId', userId);
    localStorage.setItem('token', token);
    localStorage.setItem('tokenExpiration', expirationDate.toISOString());
  }

  private getAuthData() {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    const tokenExpiration = localStorage.getItem('tokenExpiration');

    if (token && tokenExpiration) {
      return {
        userId,
        token,
        tokenExpiration
      };
    }
  }

  private clearAuthData() {
    localStorage.removeItem('userId');
    localStorage.removeItem('token');
    localStorage.removeItem('tokenExpiration');
  }
}
