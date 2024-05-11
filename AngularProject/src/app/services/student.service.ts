import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
providedIn: 'root'
})
export class StudentService {
  getAllStudents() {
    return this.http.get(BASIC_URL + "/students");
  }

	constructor(private http: HttpClient) { }

}
