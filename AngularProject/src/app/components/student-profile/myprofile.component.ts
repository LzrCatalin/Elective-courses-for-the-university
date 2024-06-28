import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../services/student.service';
import { ActivatedRoute } from '@angular/router';
import { ExtractorPdfService } from '../../services/extractor-pdf.service';
import { User } from '../../model/user.model';
import { UserService } from '../../services/user.service';
import { Student } from '../../model/student.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrl: './myprofile.component.css'
})
export class MyprofileComponent implements OnInit {
	student: Student | undefined;
	studentId?: number;
	user: User | null = null;

	constructor(
		private studentService: StudentService, 
		private route: ActivatedRoute,
		private router: Router,
		private extractorPDF: ExtractorPdfService,
		private userService: UserService
	) 
	{
		this.student;
	}

	ngOnInit(): void {
		this.user = this.userService.getUser();
    	console.log(this.user)

		this.route.params.subscribe(params => {
			this.studentId = +params['studentId']; 
			if (this.studentId) {
				this.getData(this.studentId);
			} else {
				console.log("Error finding ID. ! ! !")
			}
		});
	}

	getData(id: number) {
		this.studentService.getData(id).subscribe(
			(res) => {
				console.log("Successfully found student.")
				console.log(res)
				this.student = res;
			},
			(error) => {
				console.error("Error fetching student:", error);
			}
		)
	}

	handleButtonClick(): void {
		console.log('Button clicked!');
	}

	moveToApplications() {
		this.router.navigate([`/student/${this.user?.id}/applications`])
	}

	// Export certificate
	exportPDF() {
		this.extractorPDF.exportStudentCertificate(this.studentId!).subscribe(
			(res) => {
				console.log("Successfully received PDF response.");
				const blob = new Blob([res], { type: 'application/pdf' });
		
				const link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = 'certificate.pdf';
		
				link.click();
			},
			(error) => {
				console.error("Error exporting PDF:", error);
			}
		)
	}
}
