import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { ReadOnlyService } from '../../services/read-only.service';
import { ApplicationsService } from '../../services/applications.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  sidebarVisible: boolean = false;
  items: MenuItem[] = [];
  allocationProcessActive: boolean = false;
  readOnly: boolean = false;

  constructor(public readOnlyService: ReadOnlyService, public applicationsService: ApplicationsService) {}

  ngOnInit() {

    // Subscribe to the read-only state
		this.readOnlyService.readOnly$.subscribe(isReadOnly => {
      this.readOnly = isReadOnly;
    });

    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-fw pi-home',
        routerLink: '/admin'
      },
      {
        label: 'Courses',
        icon: 'pi pi-fw pi-book',
        items: [
          { label: 'Add Course', icon: 'pi pi-fw pi-plus', routerLink: '/admin/courses/post' },
          { label: 'View Courses', icon: 'pi pi-fw pi-list', routerLink: '/admin/courses' }
        ]
      },
      {
        label: 'Schedules',
        icon: 'pi pi-fw pi-calendar',
        items: [
          { label: 'Add Schedule', icon: 'pi pi-fw pi-plus', routerLink: '/admin/schedule/post' },
          { label: 'View Schedules', icon: 'pi pi-fw pi-list', routerLink: '/admin/schedule' }
        ]
      },
      {
        label: 'Students',
        icon: 'pi pi-fw pi-users',
        items: [
          { label: 'View Students', icon: 'pi pi-fw pi-list', routerLink: '/admin/students' }
        ]
      },
      {
        label: 'Toggle Read-Only',
        icon: 'pi pi-fw pi-pencil',
        // Press yes to confirm

        command: () => this.confirmToggleReadOnly() 


      },
      {
        label: 'Allocation Process', 
        icon: 'pi pi-fw pi-cog', 
        command: () => this.confirmAllocationProcess()
      }
    ];
  }

  toggleReadOnly() {
    const currentState = this.readOnlyService.readOnlySubject.getValue();
    this.readOnlyService.setReadOnly(!currentState);
  }
  
  confirmToggleReadOnly() {
    if (confirm("Are you sure you want to toggle read-only mode?")) {
      this.toggleReadOnly();
      location.reload();
    }
  }

  confirmAllocationProcess() {
    if (confirm("Are you sure you want to toggle Allocation Process?")) {
      this.allocationProcess();
      location.reload();
    }
  }

  allocationProcess() {
    this.applicationsService.allocationProcess().subscribe(
      () => {
        console.log("Successfully")
      },
      (err) => {
        console.log("Error")
      }
    )
    location.reload();
  }
}
