import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReadOnlyService {

  private readonly readOnlyKey = 'readOnly';
  private readOnlySubject: BehaviorSubject<boolean>;

  constructor() {
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
  }
}
