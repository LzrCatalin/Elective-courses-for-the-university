import { Component } from '@angular/core';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent {
  title="Courses"
  value: any;
  loading: boolean = false;
  courses: any[] = [];
  cols: any[] = [];
  constructor() {
    this.courses = [
      {name: 'Curs 1'},
      {name: 'Curs 2'},
      {name: 'Curs 3'},
      {name: 'Curs 4'},
      {name: 'Curs 5'},
      {name: 'Curs 6'},
    ];

    this.cols = [
      { field: 'name', header: 'Name' },
    ];
  }
  deleteRow(Index: number) {
    this.courses.splice(Index, 1);
  }
}
