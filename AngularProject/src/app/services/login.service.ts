import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { text } from 'stream/consumers';
import { Observable } from 'rxjs';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  loginRequest(email: string ): Observable<any> {
    const body = {
      email: email
    }

    return this.http.post(`${BASIC_URL}/login/verify`, body, { responseType: 'text' })
  }
}
