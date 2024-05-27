import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { ReadOnlyService } from '../../services/read-only.service';
import { ApplicationsService } from '../../services/applications.service';
import { max } from 'rxjs';

@Component({
  selector: 'app-get-courses',
  templateUrl: './get-courses.component.html',
  styleUrls: ['./get-courses.component.css']
})
export class GetCoursesComponent implements OnInit {

  courses: any[] = [];
  courseOptions: any[] = [];
  selectedCourse: any;
  studentIds: string[] = [];
  editedCourse: any;
  selectedStatus: string = "";
  dropdownUsed: boolean = false;
  showReassignForm: boolean = false;
  selectedStudent: any;
  newCourse: string = '';
  readOnly: boolean = false;


  constructor(private courseService: CourseService, 
    private readonlyService: ReadOnlyService,
    private applicationService: ApplicationsService) {}

  ngOnInit() {
    this.getAllCourses();
    this.readonlyService.readOnly$.subscribe(
      (res) => {
        this.selectedStatus = res ? 'ACCEPTED' : 'PENDING';
        this.readOnly = res;
      }
    )
  }

  // Fetch all courses
  getAllCourses() {
    this.courseService.getAllCourses().subscribe((res) => {
      this.processCourses(res);
    }, error => {
      console.error('Error fetching courses:', error);
    });
  }

  // Process the courses and group them by category
  processCourses(res: any[]) {
    if (Array.isArray(res)) {
      this.courses = res;
      this.groupCoursesByCategory();
    } else {
      console.error('Invalid response format from course service.');
    }

  }
  // Group courses by category
  groupCoursesByCategory() {
    console.log("E bine... Am intrat unde trebuie...")
    const coursesByCategory: { [key: string]: any[] } = {};
    this.courses.forEach(course => {
      if (!coursesByCategory[course.category]) {
        coursesByCategory[course.category] = [];
      }
      coursesByCategory[course.category].push({ label: course.name, value: course });
    });

    this.courseOptions = Object.keys(coursesByCategory).map(category => ({
      label: category,
      items: coursesByCategory[category]
    }));
  }

  onCourseSelect() {
    this.dropdownUsed = true;
    if (this.selectedCourse) {
      const courseId = this.selectedCourse.id;
      this.courseService.getIDs(courseId, this.selectedStatus).subscribe((data: string[]) => {
        this.studentIds = data;
      });
    }
  }

  onStatusChange(status: string) {
    this.selectedStatus = status;
    // Fetch the student IDs again when status changes if needed
    if (this.selectedCourse) {
      this.onCourseSelect();
    }
  }
  
  editCourse(course: any) {
    this.editedCourse = { ...course }
  }

  cancelEdit() {
    this.editedCourse = null;
  }

  updateCourse() {
    console.log("Update course...")
    if(this.editedCourse) {
      console.log("Inside if ...")
      const { id, name, category, studyYear, teacher, maxCapacity, facultySection, applicationsCount } = this.editedCourse;
      this.courseService.updateCourse(id, name, category, studyYear, teacher, maxCapacity, facultySection, applicationsCount)
      .subscribe(
        () => {
          console.log("Successfully edited course")
          this.cancelEdit();
          location.reload();
        },
        (error) => {
          console.log("Error ! ! !")
        }
      )
    }
  }

  deleteCourse(id: number) {
    console.log("Inside deletion function...")
    console.log(id)
    if (confirm("Are you sure that you want to delete this course?")) {
      console.log("In if statement")
      this.courseService.deleteCourse(id).subscribe(() => {
        console.log("Course deleted successfully")
        location.reload()
      }, (error) => {
        console.error("Error deleting the course: ", error);
        console.log("Error deleting the course")
        location.reload()
      }); 
    }
  }

  reassignStudent(studentId: number, currentCourse: string, newCourseName: string): void {
    // Assuming 'this.selectedCourse' holds the current course name
    this.applicationService.updateApplicationAsAdmin(studentId, currentCourse, newCourseName)
        .subscribe(
            () => {
                // Handle success
                console.log('Course updated successfully.');
            },
            (error) => {
                // Handle error
                console.error('Error updating course:', error);
            }
        );
}

  clearDropdown() {
    this.selectedCourse = null;
    this.dropdownUsed = false; 
  }

  exportPDF(courseId: number, status: string) {
    console.log("Selected course id: " + courseId)
    console.log("Selected status: " + status)
    this.courseService.exportPDF(courseId, status).subscribe(
      (res) => {
        console.log("Successfully received PDF response.");
				const blob = new Blob([res], { type: 'application/pdf' });
		
				const link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = 'allocation.pdf';
		
				link.click();
      },
      (error) => {
				console.error("Error exporting PDF:", error);
			}
		)
  }
}
