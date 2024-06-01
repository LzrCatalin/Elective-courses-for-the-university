import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Router } from 'express';
import { error } from 'console';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup = new FormGroup({});

  constructor(private loginService: LoginService,
    private fb:FormBuilder,
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
  }

}
