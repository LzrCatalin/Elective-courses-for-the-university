import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { text } from 'stream/consumers';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  loginRequest(email: string): Observable<User> {
    const body = {
      email: email
    }

    return this.http.post<User>(`${BASIC_URL}/login/verify`, body)
  }
}
