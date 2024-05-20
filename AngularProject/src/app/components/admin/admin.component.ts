import { Component } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ReadOnlyService } from '../../services/read-only.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {

	constructor(public readOnlyService: ReadOnlyService) {}

	toggleReadOnly() {
		console.log("Toggle Read-Only...")
		this.readOnlyService.readOnly$.subscribe(currentState => {
		  this.readOnlyService.setReadOnly(!currentState);
		});
	  }

}
