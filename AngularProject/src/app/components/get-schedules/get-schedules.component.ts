import { Component } from '@angular/core';
import { ScheduleService } from '../../services/schedule.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-get-schedules',
  templateUrl: './get-schedules.component.html',
  styleUrl: './get-schedules.component.css',
  providers: [MessageService]
})
export class GetSchedulesComponent {
  schedules: any[] = [];
  scheduleOptions: any[] = [];
  selectedSchedule: any;
  editedSchedule: any;
  errorMessage: string = '';

  constructor(private scheduleService: ScheduleService,
    private messageService: MessageService) {}
  
  ngOnInit() {
    this.getAllSchedules();
  }

  showMessage(severity: string, summary: string, detail: string): void {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  getAllSchedules() {
    this.scheduleService.getAllSchedules().subscribe((res) => {
      this.schedules = res;
      console.log('Schedules:', this.schedules);
    }, error => {
      console.error('Error fetching schedules:', error);
    });
  }


  onScheduleSelect() {
    this.editedSchedule = this.selectedSchedule;
  }

  updateSchedule() {
    this.scheduleService.updateSchedule(this.editedSchedule.id, this.editedSchedule.startTime, this.editedSchedule.endTime, this.editedSchedule.weekDay, this.editedSchedule.weekParity).subscribe((res) => {
      console.log('Schedule updated successfully:', res);
      this.getAllSchedules();
      location.reload();
    }, 
    (error) => {
      console.error('Error updating schedule:', error);
      this.errorMessage = error.error;
			this.showMessage('error', 'Update Schedule Error', this.errorMessage);
    });
  }

  deleteSchedule(id: number) {
    console.log("Inside deletion function...")
    console.log(id)
    if (confirm("Are you sure that you want to delete this course?")) {
      this.scheduleService.deleteSchedule(id).subscribe(() => {
        console.log("Course deleted successfully")
        location.reload()
      }, 
      (error) => {
        console.log("Error deleting the course")
        this.errorMessage = error.error;
				this.showMessage('error', 'Delete Schedule Error', this.errorMessage);
        location.reload()
      });
    }
  }

  editSchedule(schedule: any) {
    this.editedSchedule = { ...schedule };
  }

  cancelEdit() {
    this.editedSchedule = null;
  }

  clearDropdowns() {
    this.selectedSchedule = null;
    this.editedSchedule = null;
  }
}
