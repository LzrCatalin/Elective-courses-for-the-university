import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CourseService } from '../../services/course.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { CommonModule } from '@angular/common';

@Component({
selector: 'app-post-course',
templateUrl: './post-course.component.html',
styleUrl: './post-course.component.css',
providers: [MessageService]
})
export class PostCourseComponent {
postCourseForm: FormGroup = new FormGroup({});
facultysections: string[] = [];
errorMessage: string = '';


constructor(private courseService:CourseService,
	private fb:FormBuilder,
	private router: Router,
	private messageService: MessageService) { }

ngOnInit() {
	this.postCourseForm = this.fb.group({
	name: [null, Validators.required],
	category: [null, Validators.required],
	studyYear: [null, Validators.required],
	facultySection: [null, Validators.required],
	teacher: [null, Validators.required],
	maxCapacity: [null, Validators.required],
	});
	this.loadFacultySections();
}

showMessage(severity: string, summary: string, detail: string): void {
	this.messageService.add({ severity: severity, summary: summary, detail: detail });
}

loadFacultySections() {
	this.courseService.getFacultySections().subscribe((res: any) => {
	this.facultysections = res;
	console.log(res);
	});
}

postCourse() {
	console.log("Post function...\nDisplay data:\n")
	console.log(this.postCourseForm.value.name);
	console.log(this.postCourseForm.value.category);
	console.log(this.postCourseForm.value.studyYear);
	console.log(this.postCourseForm.value.facultySection);
	console.log(this.postCourseForm.value.teacher);
	console.log(this.postCourseForm.value.maxCapacity);
	this.courseService.postCourse(this.postCourseForm.value.name, 
		this.postCourseForm.value.category, this.postCourseForm.value.studyYear,
		this.postCourseForm.value.facultySection, this.postCourseForm.value.teacher,
		this.postCourseForm.value.maxCapacity).subscribe(
			(res: any) => {
				console.log(res);
				this.router.navigate(['/admin/courses/']);
			},
			(error) => {
				console.log("Error ! ! !")
				this.errorMessage = error.error;
				this.showMessage('error', 'Add Course Error', this.errorMessage);
			}
		);
	}
}
