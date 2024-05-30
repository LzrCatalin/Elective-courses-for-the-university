// src/app/get-applications/get-applications.component.ts
import { Component, OnInit } from '@angular/core';
import { ApplicationsService } from '../../services/applications.service';
import { ActivatedRoute } from '@angular/router';
import { ReadOnlyService } from '../../services/read-only.service';
import { ScheduleService } from '../../services/schedule.service';
import { MessageService } from 'primeng/api';

@Component({
	selector: 'app-get-applications',
	templateUrl: './get-applications.component.html',
	styleUrls: ['./get-applications.component.css'],
	providers: [MessageService]
})
export class GetApplicationsComponent implements OnInit {
	applications: any[] = [];
	cols: any[] = [];
	studentId?: number;
	showForm: boolean = false;
	showEditForm: boolean = false;
	courseName: string = '';
	priority: number = 0;
	editedPriority: number = 0;
	selectedApplication: any;
	readOnly: boolean = false;
	schedules: any[] = [];
	showSchedules: boolean = false;
	errorMessage: string = '';

	constructor(
		private route: ActivatedRoute,
		private applicationsService: ApplicationsService,
		private readOnlyService: ReadOnlyService,
		private scheduleService: ScheduleService,
		private messageService: MessageService
	) {}

	ngOnInit(): void {
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
        this.messageService.add({ severity: severity, summary: summary, detail: detail });
    }

	toggleFormVisibility(): void {
		this.showForm = !this.showForm;

		if (!this.showForm) {
			this.resetForm();
		}
	}

	toggleEditFormVisibility(application: any): void {
		this.selectedApplication = application;
		this.editedPriority = application.priority;
		this.showEditForm = !this.showEditForm;
	}

	onEditFormSubmit(): void {
		this.updateApplication(this.selectedApplication);
		this.selectedApplication.priority = this.editedPriority;
		this.showEditForm = false;
	}

	cancelEdit(): void {
		this.editedPriority = this.selectedApplication.priority;
		this.showEditForm = false;
	}

	resetForm(): void {
		this.courseName = '';
		this.priority = 0;
	}

	getApplicationsForStudent(studentId: number): void {
		this.applicationsService.getStudentApplications(studentId).subscribe(
			res => {
				this.applications = res;
			},
			error => {
				console.error('Error fetching applications for student:', error);
			}
		);
	}

	getApplications(): void {
		this.applicationsService.getApplications().subscribe(
			res => {
				this.applications = res;
			},
			error => {
				console.error('Error fetching applications:', error);
			}
		);
	}

	updateApplication(application: any): void {
		if (this.studentId !== undefined) {
			this.applicationsService.updateApplicationAsStudent(application.id, this.editedPriority).subscribe(
				() => {
					console.log('Successfully updated application');
					
				},
				error => {
					console.error('Error updating application:', error);
					this.errorMessage = error.error;
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
				() => {
					console.log('Application deleted successfully.');
					location.reload();
				},
				error => {
					console.error('Error deleting application:', error);
					this.errorMessage = error.error;
					this.showMessage('error', 'Delete Application Error', this.errorMessage);
				}
			);
		}
	}

	getStudentSchedules(studentId: number): void {
        console.log("Inside schedule display ... ");
        console.log("Student id: " + studentId);

        if (this.showSchedules) {
            this.showSchedules = false;
        } else {
            this.scheduleService.displayStudentSchedules(studentId).subscribe(
                (res) => {
                    console.log("Successfully displayed ... ");
                    console.log(res);
                    this.schedules = res; 
                    this.showSchedules = true;
                },
                (error) => {
                    console.log("Error", error);
					this.errorMessage = error.error;
					this.showMessage('error', 'Failed to fetch schedules', this.errorMessage);
                }
            );
        }
    }
}
