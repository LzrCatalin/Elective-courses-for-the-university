<<<<<<< HEAD
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
=======
import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { TableModule } from 'primeng/table';
import { SelectItem, FilterService, FilterMatchMode } from "primeng/api"; 
import { SortEvent } from 'primeng/api';

@Component({
	selector: 'app-course',
	templateUrl: './course.component.html',
	styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
	cols: any[] = [];
	loading: boolean = false;
	courses: any[] = [];
	matchModeOptions!: SelectItem[]; 
	
	constructor(private courseService: CourseService, private filterService: FilterService) {}

	ngOnInit() {
		this.getAllCourses();

		this.cols = [ 
            { field: "name", header: "Name" }, 
            { field: "category", header: "Category" }, 
            { field: "studyYear", header: "Study Year" }, 
			{ field: "maxCapacity", header: "Capacity"}
        ]; 
  
        this.matchModeOptions = [ 
            { label: "Starts With", value: FilterMatchMode.STARTS_WITH }, 
            { label: "Contains", value: FilterMatchMode.CONTAINS } 
        ]; 
	}
	
	getAllCourses() {
		this.courseService.getAllCourses().subscribe(
			(res) =>{
				this.courses = res;
				console.log(res);
			}
		)
	}

	handleButtonClick(): void {
		console.log('Button clicked!');
	}
>>>>>>> 7b9ab533af33e6f06032a6d69d0ba84fea038246
}
