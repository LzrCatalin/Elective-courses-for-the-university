import { Injectable } from '@angular/core';
import { User } from '../model/user.model';

@Injectable({
	providedIn: 'root'
})
export class AuthService {
	isLoggedIn = false;
	currentUser: User | undefined;

	constructor() { }

	login(user: User) {
		this.isLoggedIn = true;
		this.currentUser = user;
	}

	logout() {
		this.isLoggedIn = false;
		this.currentUser = undefined;
	}

	isAuthenticated() {
		console.log(this.isLoggedIn)
		return this.isLoggedIn;
	}

	getUser(): User | undefined {
		return this.currentUser;
	}
}
