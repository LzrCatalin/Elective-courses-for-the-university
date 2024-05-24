import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
providedIn: 'root'
})
export class StudentService {
  
	constructor(private http: HttpClient) { }

	getAllStudents() {
		return this.http.get(BASIC_URL + "/students");
	}
	
	getData(id: number): Observable<any> {
		return this.http.get(`${BASIC_URL}/students/${id}`)
	}

	exportPDF(id: number): Observable<any> {
		return this.http.get(`${BASIC_URL}/pdf/export/${id}`, { responseType: 'blob' })
	}

	getFirstYearStudents() {
		return this.http.get(`${BASIC_URL}/students/firstyear`);
	}
	getSecondYearStudents() {
		return this.http.get(`${BASIC_URL}/students/secondyear`);
	}
	getThirdYearStudents() {
		return this.http.get(`${BASIC_URL}/students/thirdyear`);
	}
}
