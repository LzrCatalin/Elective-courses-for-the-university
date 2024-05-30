import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
  providedIn: 'root'
})
export class ExtractorPdfService {

  constructor(private http: HttpClient) { }

  // Export student certificate
  exportStudentCertificate(id: number): Observable<any> {
		return this.http.get(`${BASIC_URL}/pdf/export/${id}`, { responseType: 'blob' })
	}

  // Export courses data about enrolled students
  exportCourseData(courseId: number, status: string): Observable<any> {
    const body = {
      courseId: courseId,
      status: status
    }
    return this.http.post(`${BASIC_URL}/pdf/export/courseAllocations`, body, { responseType: 'blob' })
  }

  // Export student schedule
  exportSchedule(studentId: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/pdf/export/studentSchedule/${studentId}`, { responseType: 'blob' })
  }
}
