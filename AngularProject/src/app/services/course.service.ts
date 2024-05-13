import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, max } from 'rxjs/operators';

const BASE_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) { }

  getAllCourses(): Observable<any> {
    return this.http.get(BASE_URL + "/courses").pipe(
      catchError(this.handleError)
    );
  }
  getCourseDetails(id: number): Observable<any> {
    return this.http.get(BASE_URL + "/courses/" + id).pipe(
      catchError(this.handleError)
    );
  }

  postCourse(course: any): Observable<any> {
    const url = `${BASE_URL}/courses`;
    const params = new HttpParams()
      .set('name', course.name)
      .set('category', course.category)
      .set('studyYear', course.studyYear)
      .set('teacher', course.teacher)
      .set('maxCapacity', course.maxCapacity)
      .set('facultySection', course.facultySection)
      .set('applicationsCount', course.applicationsCount);
  
    return this.http.post(url, {}, { params }).pipe(
      catchError(this.handleError)
    );
  }

  getFacultySections(): Observable<any> {
    return this.http.get<string[]>(BASE_URL + "/facultysections").pipe(
      catchError(this.handleError)
    );
  }
  
  updateCourse(id: number, courseName: string, category: string,
		studyYear: number, teacher: string, maxCapacity: number, 
		facultySection: string, applicationsCount: number
							): Observable<any> {
		const body = {
			name: courseName,
			category: category,
			studyYear: studyYear,
			teacher: teacher,
			maxCapacity: maxCapacity,
			facultySection: facultySection,
			applicationsCount: applicationsCount
		};

		return this.http.put(`${BASE_URL}/courses/${id}`, body, { responseType: 'text' })
  }
  
  deleteCourse(id: number):Observable<any> {
    return this.http.delete(BASE_URL + "/courses/" + id).pipe(
      catchError(this.handleError)
    );
  }

  getCourseById(id: number): Observable<any> {
    return this.http.get(BASE_URL + "/courses/" + id).pipe(
      catchError(this.handleError)
    );
  }
  
  getPendingIDs(courseId: number): Observable<any> {
    return this.http.get(BASE_URL + "/applications/pendingIDs/" + courseId)
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      console.error('An error occurred:', error.error.message);
    } else {
      // Server-side error
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`
      );
    }
    // Return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }

}
