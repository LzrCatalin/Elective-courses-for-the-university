import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { CourseComponent } from './components/course/course.component';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { RouterModule } from '@angular/router';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { DragDropModule } from 'primeng/dragdrop';
import { AdminComponent } from './components/admin/admin.component';
import { GetCoursesComponent } from './components/get-courses/get-courses.component';
import { StudentComponent } from './components/student/student.component';
import { GetApplicationsComponent } from './components/get-applications/get-applications.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CourseComponent,
    AdminComponent,
    GetCoursesComponent,
    StudentComponent,
    GetApplicationsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToolbarModule,
    ButtonModule,
    RouterModule,
    TableModule,
    InputTextModule,
    CascadeSelectModule,
    DragDropModule,
    HttpClientModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
