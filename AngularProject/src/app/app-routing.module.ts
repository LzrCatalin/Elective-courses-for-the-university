import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CourseComponent } from './components/course/course.component';
import { AdminComponent } from './components/admin/admin.component';
import { GetCoursesComponent } from './components/get-courses/get-courses.component';
import { StudentComponent } from './components/student/student.component';
import { GetApplicationsComponent } from './components/get-applications/get-applications.component';
import { PostCourseComponent } from './components/post-course/post-course.component';
import { GetStudentsComponent } from './components/get-students/get-students.component';
import { MyprofileComponent } from './components/myprofile/myprofile.component';

const routes: Routes = [
  {path : '', component : HomeComponent},
  {path : 'course', component : CourseComponent},
  {path : 'admin', component: AdminComponent},
  {path : 'admin/courses', component: GetCoursesComponent},
  {path : 'admin/courses/post', component: PostCourseComponent},
  {path : 'admin/students', component: GetStudentsComponent},
  {path : 'student', component : StudentComponent},
  {path : 'student/applications', component : GetApplicationsComponent},
  {path : 'student/:studentId/applications', component : GetApplicationsComponent}, 
  {path : 'student/:studentId/myprofile', component : MyprofileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
