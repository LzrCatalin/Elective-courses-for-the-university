import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { CanActivateFn } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
	providedIn: 'root'
})

export class AuthGuard implements CanActivate {

	constructor(private authService: AuthService, private router: Router) {};

	canActivate(
		next: ActivatedRouteSnapshot,
		state: RouterStateSnapshot): boolean 
	{
		console.log('CanActivate called');
	
		if (this.authService.isAuthenticated()) {
			console.log("User is authenticated");
			return true;

		} else {
			console.log("User is not authenticated, redirecting to login");
			this.router.navigate(['/']);
			return false;
		}
	}
}
