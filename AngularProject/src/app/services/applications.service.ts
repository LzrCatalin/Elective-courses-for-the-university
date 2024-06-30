import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Application } from '../model/application.model';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
  providedIn: 'root'
})
export class ApplicationsService {

	constructor(private http: HttpClient) { }

	getApplications(): Observable<any> {
		return this.http.get(`${BASIC_URL}/applications/`);
	}

	getStudentApplications(studentId: number): Observable<any> {
		return this.http.get(`${BASIC_URL}/applications/${studentId}`)
	}

	deleteApplication(id: number): Observable<any> {
		return this.http.delete(`${BASIC_URL}/applications/${id}`)
	}

	addApplication(studentId: number, courseId: number): Observable<Application> {
		const body = {
			studentId: studentId,
			courseId: courseId,
		};

		return this.http.post<Application>(`${BASIC_URL}/applications/stud`, body);
	}
	
	updateApplicationAsAdmin(studentId: number, courseName: string, newCourseName: string): Observable<Application> {
		const body = {
			studentId: studentId,
			courseName: courseName,
			newCourseName: newCourseName
		}
		return this.http.put<Application>(`${BASIC_URL}/applications/admin`, body)
	}

	updateApplicationAsStudent(id: number, priority: number): Observable<any> {
		const body = {
			priority: priority,
		};
		return this.http.put(`${BASIC_URL}/applications/stud/${id}`, body);
	}

	allocationProcess() {
		return this.http.get(`${BASIC_URL}/allocation`)
	}
}
