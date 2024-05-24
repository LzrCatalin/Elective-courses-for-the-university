import { Component } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ReadOnlyService } from '../../services/read-only.service';
import { StudentService } from '../../services/student.service';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
	data: any;
	options: any;
	chartData: any;
	chartOptions: any;

	constructor(
		public readOnlyService: ReadOnlyService,
		private studentService: StudentService,
		private courseService: CourseService) {}

	ngOnInit() {
		this.studentService.getFirstYearStudents().subscribe((res :any) => {
			this.studentService.getSecondYearStudents().subscribe((res2 :any) => {
				this.studentService.getThirdYearStudents().subscribe((res3 :any) => {
					this.data = {
						labels: ['First Year', 'Second Year	', 'Third Year'],
						datasets: [
							{
								label: 'Students',
								backgroundColor: ['#42A5F5', '#66BB6A', '#FFA726'],
								borderColor: '#BLACK',
								data: [res, res2, res3]
							}
						]
					};
				});
			},
			err => {
				console.log(err);
			});
		},
		err => {
			console.log(err);
		});
		this.options = {
			legend: {
			labels: {
				fontColor: '#495057'
			}
			},
			scales: {
			xAxes: [{
				ticks: {
				fontColor: '#495057'
				}
			}],
			yAxes: [{
				ticks: {
				fontColor: '#495057'
				}
			}]
			}
		};
		
	}
	

}
