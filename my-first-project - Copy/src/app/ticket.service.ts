import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/api/tickets'; // Update with your backend URL

  startTicketing(totalTickets: number, ticketReleaseRate: number, customerRetrievalRate: number, maxCapacity: number) {
    return axios.post(`${this.baseUrl}/start`, null, {
      params: { totalTickets, ticketReleaseRate, customerRetrievalRate, maxCapacity },
    });
  }

  stopTicketing() {
    return axios.post(`${this.baseUrl}/stop`);
  }
}
