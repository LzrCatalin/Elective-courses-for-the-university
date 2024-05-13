import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../services/student.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrl: './myprofile.component.css'
})
export class MyprofileComponent implements OnInit {
	student: any;
	studentId?: number;

	constructor(private studentService: StudentService, private route: ActivatedRoute) {
		this.student;
	}

	ngOnInit(): void {
		this.route.params.subscribe(params => {
			this.studentId = +params['studentId']; 
			if (this.studentId) {
				this.getData(this.studentId);
			} else {
				console.log("PULA")
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
}
