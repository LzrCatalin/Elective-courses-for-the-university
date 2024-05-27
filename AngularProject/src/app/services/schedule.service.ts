import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

const BASE_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  constructor(private http: HttpClient) { }

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


  postSchedule(
    courseName: string,
    startTime: string,
    endTime: string,
    weekDay: string,
    weekParity: string):Observable<any>{
      const body = {
        courseName: courseName,
        startTime: startTime,
        endTime: endTime,
        weekDay: weekDay,
        weekParity: weekParity,
      };
      return this.http.post(`${BASE_URL}/courses/schedule/`, body, { responseType: 'text' });
    }
  getWeekDays():Observable<any> {
    return this.http.get<string[]>(BASE_URL + "/weekday/").pipe(
      catchError(this.handleError)
    );
  }
  getWeekParity():Observable<any> {
    return this.http.get<string[]>(BASE_URL + "/weekparity/").pipe(
      catchError(this.handleError)
    );
  }

  getAllSchedules(): Observable<any> {
    return this.http.get(BASE_URL + "/courses/schedule/").pipe(
      catchError(this.handleError)
    );
  }

  updateSchedule(id: number, startTime: string, endTime: string, weekDay: string, weekParity: string): Observable<any> {
    const body = {

      startTime: startTime,
      endTime: endTime,
      weekDay: weekDay,
      weekParity: weekParity,
    };

    return this.http.put(`${BASE_URL}/courses/schedule/${id}`, body, { responseType: 'text' })
  }

  deleteSchedule(id: number):Observable<any> {
    return this.http.delete(BASE_URL + "/courses/schedule/" + id).pipe(
      catchError(this.handleError)
    );
  }

  getScheduleById(id: number): Observable<any> {
    return this.http.get(BASE_URL + "/courses/schedule/" + id).pipe(
      catchError(this.handleError)
    );
  }

  displayStudentSchedules(studentId: number): Observable<any> {
    return this.http.get(`${BASE_URL}/courses/schedule/student-${studentId}`)
  }
}
