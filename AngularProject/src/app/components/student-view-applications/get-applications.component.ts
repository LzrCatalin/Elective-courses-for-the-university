// src/app/get-applications/get-applications.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ReadOnlyService } from '../../services/read-only.service';
import { ScheduleService } from '../../services/schedule.service';
import { ApplicationsService } from '../../services/applications.service';
import { ExtractorPdfService } from '../../services/extractor-pdf.service';
import { StudentService } from '../../services/student.service';
import { Student } from '../../model/student.model';
import { UserService } from '../../services/user.service';
import { User } from '../../model/user.model';
import { Course } from '../../model/course.model';
import { Application } from '../../model/application.model';

@Component({
	selector: 'app-get-applications',
	templateUrl: './get-applications.component.html',
	styleUrls: ['./get-applications.component.css'],
	providers: [MessageService]
})
export class GetApplicationsComponent implements OnInit {
	student: Student | undefined;
	user: User | null = null;
	applications: Application[] = [];
	cols: any[] = [];
	studentId?: number;
	showForm: boolean = false;
	showEditForm: boolean = false;
	courseName: string = '';
	priority: number = 0;
	editedPriority: number = 0;
	selectedApplication: Application | undefined;
	readOnly: boolean = false;
	schedules: any[] = [];
	showSchedules: boolean = false;
	errorMessage: string = '';
	classmates: Student[] = [];
	selectedCourse: Course | undefined;
	showClassmates: boolean = false;
	showExportButton: boolean = false;

	constructor(
		private route: ActivatedRoute,
		private applicationsService: ApplicationsService,
		private readOnlyService: ReadOnlyService,
		private scheduleService: ScheduleService,
		private messageService: MessageService,
		private extractPDF: ExtractorPdfService,
		private studentService:  StudentService,
		private userService: UserService
	) {
		this.student;
	}

	ngOnInit(): void {

		this.user = this.userService.getUser();
    	console.log(this.user)

		// Subscribe to route parameters to get the studentId
		this.route.params.subscribe(params => {
			this.studentId = +params['studentId'];
			if (this.studentId) {
				this.getApplicationsForStudent(this.studentId);
			} else {
				this.getApplications();
			}
		});

		// Subscribe to the read-only state
		this.readOnlyService.readOnly$.subscribe(isReadOnly => {
			this.readOnly = isReadOnly;
		});

		// Set the table columns
		this.cols = [
			{ field: 'courseName', header: 'Course Name' },
			{ field: 'priority', header: 'Priority' },
			{ field: 'status', header: 'Status' }
		];
	}

	showMessage(severity: string, summary: string, detail: string): void {
		this.messageService.add({ severity, summary, detail });
	}
	
	toggleFormVisibility(): void {
		this.showForm = !this.showForm;
	
		if (!this.showForm) {
		this.resetForm();
		}
	}
	
	toggleEditFormVisibility(application: Application): void {
		this.selectedApplication = application;
		this.editedPriority = application.priority ?? 0; 
		this.showEditForm = !this.showEditForm;
	}
	
	onEditFormSubmit(): void {
		if (this.selectedApplication) {
		this.updateApplication(this.selectedApplication);
		this.selectedApplication.priority = this.editedPriority;
		this.showEditForm = false;
		}
	}
	
	cancelEdit(): void {
		if (this.selectedApplication) {
		this.editedPriority = this.selectedApplication.priority ?? 0;
		this.showEditForm = false;
		}
	}
	
	resetForm(): void {
		this.courseName = '';
		this.priority = 0;
	}
	
	getApplicationsForStudent(studentId: number): void {
		console.log("Inside get applications method for student")
		this.applicationsService.getStudentApplications(studentId).subscribe(
			(res) => {
				this.applications = res;
			},
			(error) => {
				console.error('Error fetching applications for student:', error);
			}
		);
	}
	
	getApplications(): void {
		console.log("Inside get applications method")
		this.applicationsService.getApplications().subscribe(
			(res) => {
				this.applications = res;
			},
			(error) => {
				console.error('Error fetching applications:', error);
			}
		);
	}
	
	updateApplication(application: Application): void {
		console.log("Inside update method")
		if (this.studentId !== undefined) {
			this.applicationsService.updateApplicationAsStudent(application.id!, this.editedPriority).subscribe(
				(res) => {
					console.log('Successfully updated application');
					console.log(res);
					// Re-fetch student applications after update
					this.getApplicationsForStudent(this.studentId!)

				},
				(error) => {
					console.error('Error updating application:', error);
					this.errorMessage = error.error.error;
					this.showMessage('error', 'Update Application Error', this.errorMessage);
				}
			);
		} else {
			console.error('Student ID is undefined.');
		}
	}
	
	deleteApplication(applicationId: number): void {
		if (confirm('Are you sure you want to delete this application?')) {
			this.applicationsService.deleteApplication(applicationId).subscribe(
				(res) => {
					console.log('Application deleted successfully.');
					console.log(res)
					location.reload();
				},
				(error) => {
					console.error('Error deleting application:', error);
					this.errorMessage = error.error;
					this.showMessage('error', 'Delete Application Error', this.errorMessage);
				}
			);
		}
	}
	
	getStudentSchedules(studentId: number): void {
		console.log('Inside schedule display ... ');
		console.log('Student id: ' + studentId);
	
		if (this.showSchedules) {
			this.showSchedules = false;
			this.showExportButton = false;
		} else {
			this.scheduleService.displayStudentSchedules(studentId).subscribe(
				(res) => {
					console.log('Successfully displayed ... ');
					console.log(res);
					this.schedules = res;
					this.showSchedules = true;
					this.showExportButton = res.length > 0;
				},
				(error) => {
					console.log('Error', error);
					this.errorMessage = error.error;
					this.showMessage('error', 'Failed to fetch schedules', this.errorMessage);
				}
			);
		}
	}
	
	// Export student schedule
	exportPDF(studentId: number): void {
		console.log('Student id: ' + studentId);
		this.extractPDF.exportSchedule(studentId).subscribe(
			(res) => {
				console.log('Successfully extracted PDF.');
				console.log(res);
		
				const blob = new Blob([res], { type: 'application/pdf' });
		
				const link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = 'schedule.pdf';
		
				link.click();
			},
			(error) => {
				console.error('Error exporting PDF:', error);
				this.errorMessage = error.error;
				this.showMessage('error', 'Failed to fetch schedules', this.errorMessage);
			}
		);
	}
	
	getStudentClassmates(studentId: number, courseId: number): void {
		console.log('Student id: ' + studentId);
		console.log('Course id: ' + courseId);
		this.studentService.getStudentClassmates(studentId, courseId).subscribe(
			(res: Student[]) => {
				console.log(typeof res);
				this.classmates = res;
				console.log(this.classmates);
				this.showClassmates = true;
			},
			err => {
				console.log(err);
			}
		);
	}
	
	toggleClassmatesTable(): void {
		this.showClassmates = !this.showClassmates;
	}
	
	toggleSchedulesTable(): void {
		this.showSchedules = !this.showSchedules;
		if (!this.showSchedules) {
			this.showExportButton = false;
		}
	}
}
