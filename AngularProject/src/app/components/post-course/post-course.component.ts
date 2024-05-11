import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CourseService } from '../../services/course.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-course',
  templateUrl: './post-course.component.html',
  styleUrl: './post-course.component.css'
})
export class PostCourseComponent {
  postCourseForm: FormGroup = new FormGroup({});
  facultysections: string[] = [];
  
  constructor(private courseService:CourseService,
    private fb:FormBuilder,
    private router: Router) { }
  
  ngOnInit() {
    this.postCourseForm = this.fb.group({
      name: [null, Validators.required],
      category: [null, Validators.required],
      studyYear: [null, Validators.required],
      facultySection: [null, Validators.required],
      teacher: [null, Validators.required],
      maxCapacity: [null, Validators.required],
    });
    this.loadFacultySections();
  }
  
  loadFacultySections() {
    this.courseService.getFacultySections().subscribe((res: any) => {
      this.facultysections = res;
      console.log(res);
    });
  }

  postCourse() {
    console.log(this.postCourseForm.value);
    this.courseService.postCourse(this.postCourseForm.value).subscribe((res: any) => {
      console.log(res);
      this.router.navigate(['']);
    });
  }
}
