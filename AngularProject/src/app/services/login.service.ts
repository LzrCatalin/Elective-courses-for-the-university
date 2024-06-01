import { Injectable } from '@angular/core';

const BASE_URL = 'http://localhost:8080';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor() { }

  login(email: string ){
    // --- to be implemented ---
  }
}
