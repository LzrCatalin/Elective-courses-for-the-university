import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CourseComponent } from './components/course/course.component';
import { AdminComponent } from './components/admin/admin.component';
import { GetCoursesComponent } from './components/get-courses/get-courses.component';
import { GetStudentsComponent } from './components/get-students/get-students.component';
import { PostCourseComponent } from './components/post-course/post-course.component';

const routes: Routes = [
  {path : '', component : HomeComponent},
  {path : 'course', component : CourseComponent},
  {path : 'admin', component: AdminComponent},
  {path : "admin/courses", component: GetCoursesComponent},
  {path : 'admin/students', component: GetStudentsComponent},
  {path : 'admin/courses/post', component: PostCourseComponent},
  {path : 'admin/courses/:id', component: PostCourseComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
