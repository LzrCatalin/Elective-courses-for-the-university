import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup = new FormGroup({});

  constructor(private loginService: LoginService,
    private fb:FormBuilder,
    private router: Router
    ) { }
    
  ngOnInit(){
    this.loginForm = this.fb.group({
      email: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  login() {
    console.log("\nAm apasat pe login...");
    console.log("\nEmailul: ")
    console.log(this.loginForm.value.email);
    console.log("\nParola: ")
    console.log(this.loginForm.value.password);

    this.loginService.loginRequest(this.loginForm.value.email).subscribe(
      (res) => {
        console.log("Successfully made request")
        console.log(res)

        if (res == "student") {
          this.router.navigate(["/home"])
        } else {
          this.router.navigate(["/admin"])
        }

      },
      (err) => {
        console.log("Error", err);
      }
    )
  }


}
