import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule for ngIf, ngFor, etc.
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';  // Import FormsModule for ngModel
import { TicketService } from '../ticket.service';  // Import the service

@Component({
  selector: 'app-ticket-control',
  templateUrl: './ticket-control.component.html',
  styleUrls: ['./ticket-control.component.css'],
  standalone: true, // Mark as standalone
  imports: [
    CommonModule, 
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule  // Add FormsModule to imports array
  ],
  providers: [TicketService]  // Explicitly provide the service here
})
export class TicketControlComponent {
  totalTickets: number = 0;
  ticketReleaseRate: number = 0;
  customerRetrievalRate: number = 0;
  maxCapacity: number = 0;

  constructor(private ticketService: TicketService) {}

  startTicketing() {
    this.ticketService
      .startTicketing(this.totalTickets, this.ticketReleaseRate, this.customerRetrievalRate, this.maxCapacity)
      .then(() => alert('Ticketing started successfully!'))
      .catch(err => console.error('Error starting ticketing:', err));
  }

  stopTicketing() {
    this.ticketService
      .stopTicketing()
      .then(() => alert('Ticketing stopped successfully!'))
      .catch(err => console.error('Error stopping ticketing:', err));
  }
}
