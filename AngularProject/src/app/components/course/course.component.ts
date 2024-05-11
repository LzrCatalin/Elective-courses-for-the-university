import { Component } from '@angular/core';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrl: './course.component.css'
})

export class CourseComponent {
  [x: string]: any;

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
  deleteCourse(id: number){
    console.log("Inside deletion function...")
    console.log(id)
    if(confirm("Are you sure that you want to delete this course?")){
      console.log("In if statement")
      this.courseService.deleteCourse(id).subscribe(()=>{
        console.log("Course deleted successfully")
        location.reload()
      },
      (error) => {
        console.error("Error deleting the course: ", error);
        console.log("Error deleting the course")
        location.reload()
      });
    }
  }
}
