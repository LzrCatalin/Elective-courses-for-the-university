import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { SelectItem, FilterService, FilterMatchMode } from "primeng/api"; 
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationsService } from '../../services/applications.service';
import { MessageService } from 'primeng/api';
import { ReadOnlyService } from '../../services/read-only.service';
import { Course } from '../../model/course.model';
import { User } from '../../model/user.model';
import { UserService } from '../../services/user.service';


@Component({
    selector: 'app-course',
    templateUrl: './course.component.html',
    styleUrls: ['./course.component.css'],
    providers: [MessageService]

})
export class CourseComponent implements OnInit {
    user: User | null = null;
    cols: any[] = [];
    loading: boolean = false;
    courses: Course[] = [];
    matchModeOptions!: SelectItem[];
    studentId?: number;
    readOnly: boolean = false; 
    showForm: boolean = false;
    priority: number = 0;
    selectedCourse: Course | undefined;
    errorMessage: string = '';
    
    constructor(
        private userService: UserService,
        private courseService: CourseService, 
        private filterService: FilterService,
        private route: ActivatedRoute,
        private activateRouter: ActivatedRoute,
        private router: Router,
        private applicationsService: ApplicationsService,
        private messageService: MessageService,
        private readOnlyService: ReadOnlyService,

    ) {}

    ngOnInit() {

        this.user = this.userService.getUser();
        console.log(this.user)

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

        // Subscribe to the read-only state
		this.readOnlyService.readOnly$.subscribe(isReadOnly => {
			this.readOnly = isReadOnly;
		});

    }
    
    getAllCourses() {
        this.courseService.getAllCourses().subscribe(
            (res) =>{
                this.courses = res;
                console.log(res);
            }
        )
    }

    addApplication(course: Course, studentId: number) {
        this.applicationsService.addApplication(studentId, course.id!).subscribe(
            (res) => {
                console.log("Successfully added new application")
                console.log(res)
                this.router.navigate(["/student/" + this.studentId + "/applications"])
            },
            (error) => {
                console.error('Error adding application:', error);
               // Display the error message
                this.errorMessage = error.error.error;
                console.log(this.errorMessage)
				this.showMessage('error', 'Add Application Error', this.errorMessage);
            }
        )
    }

    // Display error messages sent from backend
    showMessage(severity: string, summary: string, detail: string): void {
        this.messageService.add({ severity: severity, summary: summary, detail: detail });
    }


    handleButtonClick(): void {
        console.log('Button clicked!');
        
    }
}
