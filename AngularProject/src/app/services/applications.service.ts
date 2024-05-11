import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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

	addApplication(studentId: number, courseName: string, priority: number): Observable<any> {
		const body = {
			studentId: studentId,
			courseName: courseName,
			priority: priority,
		};
		return this.http.post(`${BASIC_URL}/applications/stud`, body, { responseType: 'text' });
	}
	
	updateApplication(id: number, priority: number): Observable<any> {
		const body = {
			priority: priority,
		};
		return this.http.put(`${BASIC_URL}/applications/stud/${id}`, body);
	}
}
