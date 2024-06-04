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

const routes: Routes = [

  {path: '', component : LoginComponent},
  {path : 'home', component : HomeComponent},
  {path : 'course', component : CourseComponent},
  {path : 'admin', component: AdminComponent},
  {path : 'admin/courses', component: GetCoursesComponent},
  {path : 'admin/courses/post', component: PostCourseComponent},
  {path : 'admin/schedule', component: GetSchedulesComponent},
  {path : 'admin/schedule/post', component : PostScheduleComponent},
  {path : 'admin/students', component: GetStudentsComponent},
  {path : 'student/applications', component : GetApplicationsComponent},
  {path : 'student/:studentId/applications', component : GetApplicationsComponent}, 
  {path : 'student/:studentId/myprofile', component : MyprofileComponent},
  {path : 'student/:studentId/courses', component : CourseComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
