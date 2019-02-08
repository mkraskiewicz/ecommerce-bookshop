import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class UserService {
 
  private userUrl = 'http://localhost:8080/api/test/user';
  private adminUrl = 'http://localhost:8080/api/test/admin';
  private forgotUrl = 'http://localhost:8080/api/auth/forgot-password';
 
  constructor(private http: HttpClient) { }
 
  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, { responseType: 'text' });
  }
 
  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }

  remindEmail(email : string) {
    return this.http.post( this.forgotUrl + "?email=" + email, httpOptions);
  }

  resetPassword(userToken: string, userPassword: string) {
    return this.http.post(this.forgotUrl + '/reset', {
      token: userToken,
      password: userPassword
    }, httpOptions);
  }

}