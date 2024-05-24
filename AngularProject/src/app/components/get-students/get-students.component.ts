import { Component } from '@angular/core';
import { StudentService } from '../../services/student.service';
import { FilterMatchMode, SelectItem } from 'primeng/api';

@Component({
  selector: 'app-get-students',
  templateUrl: './get-students.component.html',
  styleUrl: './get-students.component.css'
})
export class GetStudentsComponent {

  students: any[] = [];
  matchModeOptions!: SelectItem[]; 

  constructor(private studentService: StudentService) {}

  ngOnInit(){
    console.log('GetStudentsComponent initialized');
    this.getAllStudents();
    this.matchModeOptions = [ 
      { label: "Starts With", value: FilterMatchMode.STARTS_WITH }, 
      { label: "Contains", value: FilterMatchMode.CONTAINS } 
  ]; 
  }
  getAllStudents() {
    console.log('Getting all students');

    this.studentService.getAllStudents().subscribe((res :any) => {
      this.students = res;
      console.log(res);
    })
  }

}
