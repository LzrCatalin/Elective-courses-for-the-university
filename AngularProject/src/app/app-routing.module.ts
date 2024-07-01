import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/student-home/home.component';
import { CourseComponent } from './components/student-view-courses/course.component';
import { AdminComponent } from './components/admin-dashboard/admin.component';
import { GetCoursesComponent } from './components/admin-view-courses/get-courses.component';
import { GetApplicationsComponent } from './components/student-view-applications/get-applications.component';
import { PostCourseComponent } from './components/admin-add-course/post-course.component';
import { GetStudentsComponent } from './components/admin-view-students/get-students.component';
import { MyprofileComponent } from './components/student-profile/myprofile.component';
import { PostScheduleComponent } from './components/admin-add-schedule/post-schedule.component';
import { GetSchedulesComponent } from './components/admin-view-schedules/get-schedules.component';
import { LoginComponent } from './components/login/login.component';
import { AuthService } from './auth/auth.service'
import { AuthGuard } from './auth/auth.guard'

const routes: Routes = [

  {path: '', component : LoginComponent},
  {path : 'course', component : CourseComponent, canActivate:[AuthGuard]},
  {path : 'admin', component: AdminComponent, canActivate:[AuthGuard]},
  {path : 'admin/courses', component: GetCoursesComponent, canActivate:[AuthGuard]},
  {path : 'admin/courses/post', component: PostCourseComponent, canActivate:[AuthGuard]},
  {path : 'admin/schedule', component: GetSchedulesComponent, canActivate:[AuthGuard]},
  {path : 'admin/schedule/post', component : PostScheduleComponent, canActivate:[AuthGuard]},
  {path : 'admin/students', component: GetStudentsComponent, canActivate:[AuthGuard]},
  {path : 'home/:studentId', component : HomeComponent, canActivate:[AuthGuard]},
  {path : 'student/:studentId/applications', component : GetApplicationsComponent, canActivate:[AuthGuard]}, 
  {path : 'student/:studentId/myprofile', component : MyprofileComponent, canActivate:[AuthGuard]},
  {path : 'student/:studentId/courses', component : CourseComponent, canActivate:[AuthGuard]},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
