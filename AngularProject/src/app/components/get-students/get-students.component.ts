import { Component } from '@angular/core';
import { StudentService } from '../../services/student.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-get-students',
  templateUrl: './get-students.component.html',
  styleUrl: './get-students.component.css'
})
export class GetStudentsComponent {

  students: any[] = [];

  constructor(private studentService: StudentService) {}

  ngOnInit(){
    console.log('GetStudentsComponent initialized');
    this.getAllStudents();
  }
  getAllStudents() {
    console.log('Getting all students');

    this.studentService.getAllStudents().subscribe((res :any) => {
      this.students = res;
      console.log(res);
    })
  }
}
