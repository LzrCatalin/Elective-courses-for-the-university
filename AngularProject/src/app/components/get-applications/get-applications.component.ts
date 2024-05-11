import { Component } from '@angular/core';
import { ApplicationsService } from '../../services/applications.service';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'app-get-applications',
	templateUrl: './get-applications.component.html',
	styleUrl: './get-applications.component.css'
})
export class GetApplicationsComponent {
	applications: any[] = [];
	studentId?: number;
	showForm: boolean = false;
	showEditForm: boolean = false;
	courseName: string = '';
	priority: number = 0;
	editedPriority: number = 0;
	selectedApplication: any;

	constructor(private route: ActivatedRoute, private applicationsService: ApplicationsService) {
		this.applications = [];
	}

	ngOnInit() {
		this.route.params.subscribe(params => {
			this.studentId = +params['studentId']; 
			if (this.studentId) {
				this.getApplicationsForStudent(this.studentId);
			} else {
				this.getApplications();
			}
		});
	}

	toggleFormVisibility() {
		this.showForm = !this.showForm; 

		if (!this.showForm) {
			this.resetForm();
		}
	}

	toggleEditFormVisibility(application: any) {
		this.selectedApplication = application;
		this.editedPriority = application.priority; 
		this.showEditForm = !this.showEditForm;
	}

	onFormSubmit() {
		this.addApplication();
		this.showForm = false;
		this.resetForm();
	}
	
	onEditFormSubmit() {
		this.updateApplication(this.selectedApplication)
		this.selectedApplication.priority = this.editedPriority;
		this.showEditForm = false;
	}

	cancelEdit() {
		this.editedPriority = this.selectedApplication.priority;
		this.showEditForm = false;
	}

	resetForm() {
		this.courseName = '';
		this.priority = 0;
	}

	getApplicationsForStudent(studentId: number) {
		this.applicationsService.getStudentApplications(studentId).subscribe(
			(res) => {
				console.log("Successfully displayed applications for student:" + studentId);
				//console.log(res);
				this.applications = res;
			},
			(error) => {
				console.error("Error fetching applications for student:", error);
			}
		)
	}

	getApplications() {
		this.applicationsService.getApplications().subscribe(
			(res) => {
				console.log("Successfully displayed applications")
				//console.log(res);
				this.applications = res;

			},
			(error) => {
				console.error("Error fetching applications", error);
			}
		)
	}

	addApplication() {
		if (this.studentId !== undefined) {
			console.log("Inside first if...")
			console.log(this.courseName)
			console.log(this.priority)
			this.applicationsService.addApplication(this.studentId, this.courseName, this.priority).subscribe(
				(res) => {
					console.log("Successfully added new application");
					location.reload();
				},
				(error) => {
					console.error("Error adding application:", error);
				}
			);

		} else {
			console.error("Student ID is undefined.");
		}
		console.log("Final state")
	}

	updateApplication(application: any) {
		console.log("Application id: " + application.id)
		console.log("Course name: " + application.course.name)
		console.log("Past priority: " + application.priority)
		if (this.studentId !== undefined) {
			console.log("Student id: " + this.studentId)
			console.log("New priority: " + this.editedPriority)
			this.applicationsService.updateApplication(application.id, this.editedPriority).subscribe(
				(res) => {
					console.log("Successfully updated application");
					console.log("New priority: " + this.editedPriority)
					location.reload()
				},
				(error) => {
					console.log("Error updating application:", error);
				}
			);
		} else {
			console.log("Student id is undefined")
		}
		console.log("Final state")
	}

	deleteApplication(applicationId: number) {
		console.log("Inside deletion function...")
		console.log(applicationId)
		if (confirm("Are you sure you want to delete this application?")) {
			console.log("Inside if statement")
			this.applicationsService.deleteApplication(applicationId).subscribe(
				() => {
					console.log("Application deleted successfully.");
					location.reload();
				},
				(error) => {
					console.error("Error deleting application:", error);
					console.log("Error deleting the application...")
					location.reload();
				}
			);
		}
	}
}
