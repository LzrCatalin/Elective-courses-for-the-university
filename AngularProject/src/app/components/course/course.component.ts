import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { SelectItem, FilterService, FilterMatchMode } from "primeng/api"; 
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationsService } from '../../services/applications.service';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-course',
    templateUrl: './course.component.html',
    styleUrls: ['./course.component.css'],
    providers: [MessageService]

})
export class CourseComponent implements OnInit {
    cols: any[] = [];
    loading: boolean = false;
    courses: any[] = [];
    matchModeOptions!: SelectItem[];
    studentId?: number;
    readOnly: boolean = false; 
    showForm: boolean = false;
    priority: number = 0;
    selectedCourse: any;
    errorMessage: string = '';
    
    constructor(
        private courseService: CourseService, 
        private filterService: FilterService,
        private route: ActivatedRoute,
        private activateRouter: ActivatedRoute,
        private router: Router,
        private applicationsService: ApplicationsService,
        private messageService: MessageService

    ) {}

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.studentId = +params['studentId'];
        });

        this.getAllCourses();

        this.cols = [ 
            { field: "name", header: "Name" }, 
            { field: "category", header: "Category" }, 
            { field: "studyYear", header: "Study Year" }, 
            { field: "maxCapacity", header: "Capacity"},
            { field: "facultySection", header: "Section"}
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
    
    toggleFormVisibility(course: any): void {
        this.showForm = !this.showForm;
        this.selectedCourse = course;
        
        if (!this.showForm) {
            this.resetForm();
        }
    }

    resetForm(): void {
        this.priority = 0;
    }

    onFormSubmit(): void {
        this.addApplication();
        this.showForm = false;
        this.resetForm();
    }

    // Add aplication implementation
    addApplication(): void {
        if (this.studentId !== undefined && this.selectedCourse) {
            this.applicationsService.addApplication(this.studentId, this.selectedCourse.name, this.priority).subscribe(
                (res) => {
                    console.log('Successfully added new application');
                    console.log(res);
                    // Navigate to student applications page after adding new application
                    this.router.navigate(["/student/" + this.studentId + "/applications"])
                },
                (error) => {
                    console.error('Error adding application:', error);
                    // Display the error message
                    this.errorMessage = error.error;
					this.showMessage('error', 'Delete Application Error', this.errorMessage);
                }
            );
        } else {
            console.error('Student ID or selected course is undefined.');
        }
    }

    // Display error messages sent from backend
    showMessage(severity: string, summary: string, detail: string): void {
        this.messageService.add({ severity: severity, summary: summary, detail: detail });
    }


    handleButtonClick(): void {
        console.log('Button clicked!');
    }
}
