import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { PostCourseComponent } from './components/post-course/post-course.component';
import { GetStudentsComponent } from './components/get-students/get-students.component';
import { DropdownModule } from 'primeng/dropdown';
import { SidebarModule } from 'primeng/sidebar';
import { AvatarModule } from 'primeng/avatar';
import { IconFieldModule } from 'primeng/iconfield';
import { TagModule} from 'primeng/tag';
import { MyprofileComponent } from './components/myprofile/myprofile.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { PostScheduleComponent } from './components/post-schedule/post-schedule.component';
import { GetSchedulesComponent } from './components/get-schedules/get-schedules.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ChartModule } from 'primeng/chart';
import { PanelMenuModule } from 'primeng/panelmenu';
import { BadgeModule } from 'primeng/badge';
import { MeterGroupModule } from 'primeng/metergroup';
import { CardModule } from 'primeng/card';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { LoginComponent } from './components/login/login.component';
import { FloatLabelModule } from 'primeng/floatlabel';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CourseComponent,
    AdminComponent,
    GetCoursesComponent,
    StudentComponent,
    GetApplicationsComponent,
    PostCourseComponent,
    GetStudentsComponent,
    MyprofileComponent,
    ToolbarComponent,
    SidebarComponent,
    PostScheduleComponent,
    GetSchedulesComponent,
    LoginComponent,

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
    HttpClientModule,
    ReactiveFormsModule,
    DropdownModule,
    SidebarModule,
    AvatarModule,
    IconFieldModule,
    InputTextModule,
    TagModule,
    ConfirmDialogModule,
    ConfirmPopupModule,
    ChartModule,
    PanelMenuModule,
    BadgeModule,
    MeterGroupModule,
    CardModule,
    MessagesModule,
    MessageModule,
    FloatLabelModule,
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
