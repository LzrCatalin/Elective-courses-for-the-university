import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { read } from 'fs';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
  providedIn: 'root'
})
export class ReadOnlyService {

  public readonly readOnlyKey = 'readOnly';
  public readOnlySubject: BehaviorSubject<boolean>;

  constructor(private http: HttpClient) {
    const savedState = localStorage.getItem(this.readOnlyKey);
    const initialState = savedState !== null ? JSON.parse(savedState) : false;
    this.readOnlySubject = new BehaviorSubject<boolean>(initialState);

  }

  get readOnly$() {
    return this.readOnlySubject.asObservable();
  }

  setReadOnly(state: boolean) {
    this.readOnlySubject.next(state);
    localStorage.setItem(this.readOnlyKey, JSON.stringify(state));
    this.sendState(state);
  }

  sendState(readOnly: boolean) {
    console.log("State Received: " + readOnly);
    console.log("State Type: " + typeof readOnly);
    this.http.post(`${BASIC_URL}/admins/readOnly`, { readOnly }).subscribe({
      // next: response => console.log('State sent successfully'),
      // error: err => console.error('Error sending state', err)
    });
  }
}
