import { Component } from '@angular/core';
import { TicketControlComponent } from './ticket-control/ticket-control.component';

@Component({
  selector: 'app-root',
  template: `
    <div class="app-container">
      <app-ticket-control></app-ticket-control>
    </div>
  `,
  styleUrls: ['./app.component.css'],
  standalone: true,  // Mark as standalone
  imports: [TicketControlComponent]
})
export class AppComponent {
  title = 'Ticket Manager';
}
