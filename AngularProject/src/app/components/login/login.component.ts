import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { Student } from '../../model/student.model';
import { User } from '../../model/user.model';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup = new FormGroup({});
  loggedUser: User | undefined;

  constructor(private loginService: LoginService,
    private fb:FormBuilder,
    private router: Router,
    private userService: UserService,
    private authService: AuthService
    ) { }
    
  ngOnInit(){
    this.loginForm = this.fb.group({
      email: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  login() {
    // console.log("\nAm apasat pe login...");
    // console.log("\nEmailul: ")
    // console.log(this.loginForm.value.email);
    // console.log("\nParola: ")
    // console.log(this.loginForm.value.password);

    this.loginService.loginRequest(this.loginForm.value.email).subscribe(
      (res) => {
        console.log("Successfully made request")
        this.loggedUser = res;
        console.log(this.loggedUser?.role)

        if (this.loggedUser) {
          this.authService.login(this.loggedUser);
          this.userService.setUser(this.loggedUser);

          if (this.loggedUser.role === "student") {
            this.router.navigate([`/home/${this.loggedUser.id}`]);
          } else {
            this.router.navigate([`/admin`]);
          }
        } 
      },
      (err) => {
        console.log("Error", err);
      }
    )
  }
}
