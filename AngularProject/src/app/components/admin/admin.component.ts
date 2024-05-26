import { Component } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ReadOnlyService } from '../../services/read-only.service';
import { StudentService } from '../../services/student.service';
import { CourseService } from '../../services/course.service';
import { MeterItem } from 'primeng/metergroup';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})

export class AdminComponent{
	data: any;
	options: any;
	chartData: any;
	chartOptions: any;
	value: MeterItem[]|undefined;
	size: any;

	constructor(
		public readOnlyService: ReadOnlyService,
		private studentService: StudentService,
		private courseService: CourseService) {}

		ngOnInit() {
			this.studentService.getFirstYearStudents().subscribe(
			  (firstYearRes: any) => {
				this.studentService.getSecondYearStudents().subscribe(
				  (secondYearRes: any) => {
					this.studentService.getThirdYearStudents().subscribe(
					  (thirdYearRes: any) => {
						this.data = {
						  labels: ['Year'],
						  datasets: [
							{
							  label: 'First Year',
							  backgroundColor: '#988686',
							  borderColor: '#000000',
							  data: [firstYearRes]
							},
							{
							  label: 'Second Year',
							  backgroundColor: '#C8B4D0',
							  borderColor: '#000000',
							  data: [secondYearRes]
							},
							{
							  label: 'Third Year',
							  backgroundColor: '#5c4e4e',
							  borderColor: '#000000',
							  data: [thirdYearRes]
							}
						  ]
						};
					  },
					  err => {
						console.log('Error fetching third year students:', err);
					  }
					);
				  },
				  err => {
					console.log('Error fetching second year students:', err);
				  }
				);
			  },
			  err => {
				console.log('Error fetching first year students:', err);
			  }
			);
		
			this.options = {
			  legend: {
				labels: {
				  fontColor: '#495057'
				}
			  },
			  scales: {
				xAxes: [
				  {
					ticks: {
					  fontColor: '#495057'
					}
				  }
				],
				yAxes: [
				  {
					ticks: {
					  fontColor: '#495057'
					}
				  }
				]
			  }
			};

			this.value = [
				{ label: 'Students', value: 40, color: '#42A5F5', icon: 'pi pi-user',},
				{ label: 'Courses', value: 30, color: '#FFA726', icon: 'pi pi-book' },
				{ label: 'Read-Only', color: '#AB47BC', icon: 'pi pi-exclamation-triangle' },
			];
		  }

}
