import { Component } from '@angular/core';
import { ScheduleService } from '../../services/schedule.service';

@Component({
  selector: 'app-get-schedules',
  templateUrl: './get-schedules.component.html',
  styleUrl: './get-schedules.component.css'
})
export class GetSchedulesComponent {
  schedules: any[] = [];
  scheduleOptions: any[] = [];
  selectedSchedule: any;
  editedSchedule: any;

  constructor(private scheduleService: ScheduleService) {}
  
  ngOnInit() {
    this.getAllSchedules();
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
    }, error => {
      console.error('Error updating schedule:', error);
    });
  }

  deleteSchedule(id: number) {
    console.log("Inside deletion function...")
    console.log(id)
    if (confirm("Are you sure that you want to delete this course?")) {
      console.log("In if statement")
      this.scheduleService.deleteSchedule(id).subscribe(() => {
        console.log("Course deleted successfully")
        location.reload()
      }, (error) => {
        console.error("Error deleting the course: ", error);
        console.log("Error deleting the course")
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
