import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ScheduleService } from '../../services/schedule.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-post-schedule',
  templateUrl: './post-schedule.component.html',
  styleUrl: './post-schedule.component.css',
  providers: [MessageService]
})
export class PostScheduleComponent implements OnInit{
  postScheduleForm: FormGroup = new FormGroup({});
  weekDays: string[] = [];
  weekParity: string[] = [];
  errorMessage: string = '';

  constructor(private scheduleService: ScheduleService,
    private fb: FormBuilder,
    private router: Router,
    private messageService: MessageService){}

  ngOnInit(){
    this.postScheduleForm = this.fb.group({
      courseName:[null,Validators.required],
      startTime:[null,Validators.required],
      endTime:[null,Validators.required],
      weekDay:[null,Validators.required],
      weekParity:[null,Validators.required],
    });
    this.loadWeekDays();
    this.loadWeekParity();
  }

  showMessage(severity: string, summary: string, detail: string): void {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  loadWeekDays(){
    this.scheduleService.getWeekDays().subscribe((res: any) =>{
      this.weekDays = res;
      console.log(res);
    });
  }

  loadWeekParity(){
    this.scheduleService.getWeekParity().subscribe((res: any) =>{
      this.weekParity = res;
      console.log(res);
    });
  }

  postSchedule(){
    console.log("Post function...\nDisplay data:\n")
    console.log(this.postScheduleForm.value.courseName);
    console.log(this.postScheduleForm.value.startTime);
    console.log(this.postScheduleForm.value.endTime);
    console.log(this.postScheduleForm.value.weekDay);
    console.log(this.postScheduleForm.value.weekParity);

    this.scheduleService.postSchedule(this.postScheduleForm.value.courseName,
      this.postScheduleForm.value.startTime,
      this.postScheduleForm.value.endTime,
      this.postScheduleForm.value.weekDay,
      this.postScheduleForm.value.weekParity).subscribe(
        (res:any) => {
          console.log(res);
          this.router.navigate(['/admin/schedule/']);

          
        },
        (error) => {
          console.log("Error!!")
          this.errorMessage = error.error;
					this.showMessage('error', 'Add Schedule Error', this.errorMessage);
        }
      );
  }
  
}
