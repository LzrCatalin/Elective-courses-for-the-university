import { Component } from '@angular/core';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-get-courses',
  templateUrl: './get-courses.component.html',
  styleUrl: './get-courses.component.css'
})
export class GetCoursesComponent {

    courses: any[] = [];

    constructor(private courseService: CourseService) {}
    ngOnInit() {
      this.getAllCourses();
    }
    getAllCourses() {
      this.courseService.getAllCourses().subscribe((res) =>{
        this.courses = res;
        console.log(res);
      })
    }
}
