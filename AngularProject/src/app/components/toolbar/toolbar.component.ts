import { Component } from '@angular/core';
import { User } from '../../model/user.model';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.css'
})
export class ToolbarComponent {
  user: User | null = null;

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.user = this.userService.getUser();
    console.log(this.user)
  }

  navigateToHome() {
    this.router.navigate([`/home/${this.user?.id}`])
  }

  navigateToCourses() {
    this.router.navigate([`/student/${this.user?.id}/courses`])
  }

  navigateToApplications() {
    this.router.navigate([`/student/${this.user?.id}/applications`])
  }

  navigateToProfile() {
    this.router.navigate([`/student/${this.user?.id}/myprofile`])
  }
}
