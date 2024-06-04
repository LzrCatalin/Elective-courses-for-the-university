import { Component, OnInit, OnDestroy } from '@angular/core';
import { ReadOnlyService } from '../../services/read-only.service';
import { StudentService } from '../../services/student.service';
import { CourseService } from '../../services/course.service';
import { MeterItem } from 'primeng/metergroup';
import { Subscription, forkJoin } from 'rxjs';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {
  data: any;
  options: any;
  value: MeterItem[] = [];
  readOnlySubscription: Subscription | undefined;

  constructor(
    public readOnlyService: ReadOnlyService,
    private studentService: StudentService,
    private courseService: CourseService
  ) {}

  ngOnInit() {
    this.initializeChartData();
    this.initializeMeterItems();
  }

  ngOnDestroy() {
    // Clean up the subscription when the component is destroyed
    if (this.readOnlySubscription) {
      this.readOnlySubscription.unsubscribe();
    }
  }

  private initializeChartData(): void {
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
  }

  private initializeMeterItems(): void {
    forkJoin([
      this.studentService.getAllStudents(),
      this.courseService.getAllCourses()
    ]).subscribe(
      ([studentsRes, coursesRes]: [any, any]) => {
        this.value = [
          { label: 'Students', value: studentsRes.length, color: '#42A5F5', icon: 'pi pi-user' },
          { label: 'Courses', value: coursesRes.length, color: '#FFA726', icon: 'pi pi-book' },
          { label: 'Read-Only', value: null, color: '#AB47BC', icon: 'pi pi-exclamation-triangle' }
        ];
        this.subscribeToReadOnlyChanges();
      },
      err => {
        console.log('Error fetching data:', err);
      }
    );
  }

  private subscribeToReadOnlyChanges(): void {
    this.readOnlySubscription = this.readOnlyService.readOnly$.subscribe(currentState => {
      this.updateReadOnlyLabelColor(currentState);
    });
  }

  private updateReadOnlyLabelColor(isReadOnly: boolean): void {
    const newColor = isReadOnly ? 'red' : '#e4e5e8';
    const readOnlyItem = this.value.find(item => item.label === 'Read-Only');
    if (readOnlyItem) {
      readOnlyItem.color = newColor;
    }
  }
}
