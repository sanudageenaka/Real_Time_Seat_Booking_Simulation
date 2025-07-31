package org.example.ticketmanager.controller;


import org.example.ticketmanager.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/start")
    public String startTicketing(@RequestParam int totalTickets,
                                 @RequestParam int ticketReleaseRate,
                                 @RequestParam int customerRetrievalRate,
                                 @RequestParam int maxCapacity) {
        ticketService.startTicketing(totalTickets, ticketReleaseRate, customerRetrievalRate, maxCapacity);
        return "Ticketing started successfully!";
    }

    @PostMapping("/stop")
    public String stopTicketing() {
        ticketService.stopTicketing();
        return "Ticketing stopped successfully!";
    }
}
