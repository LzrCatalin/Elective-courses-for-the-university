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
}
