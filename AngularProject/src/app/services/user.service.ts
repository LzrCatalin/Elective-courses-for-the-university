import { Injectable } from '@angular/core';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private user: User | null =  null;

  constructor() { }

  getUser(): User | null {
    return this.user;
  }
  setUser(user: User) {
    this.user = user;
  }
}
