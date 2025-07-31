package org.example.ticketmanager.service;

import org.example.ticketmanager.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TicketService {

    private final Queue<Ticket> ticketQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService producerPool = Executors.newSingleThreadExecutor();
    private final ExecutorService consumerPool = Executors.newFixedThreadPool(3);

    private boolean isRunning = false;
    private int ticketCounter = 0;

    public synchronized void startTicketing(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxCapacity) {
        if (isRunning) {
            System.out.println("Ticketing is already running.");
            return;
        }

        isRunning = true;

        // Producer thread to release tickets
        producerPool.submit(() -> {
            int ticketsProduced = 0;
            while (isRunning && ticketsProduced < totalTickets) {
                if (ticketQueue.size() < maxCapacity) {
                    ticketCounter++;
                    Ticket ticket = new Ticket(ticketCounter, "Event" + ticketCounter, 50.00);
                    ticketQueue.add(ticket);
                    ticketsProduced++;
                    System.out.println("Ticket added by - " + Thread.currentThread().getName() +
                            " - current size is - " + ticketQueue.size());
                }
                try {
                    Thread.sleep(1000 / ticketReleaseRate);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Producer finished releasing tickets.");
        });

        // Consumer threads to handle ticket retrieval
        for (int i = 0; i < 3; i++) {
            consumerPool.submit(() -> {
                while (isRunning || !ticketQueue.isEmpty()) {
                    Ticket ticket = ticketQueue.poll();
                    if (ticket != null) {
                        System.out.println("Ticket bought by - " + Thread.currentThread().getName() +
                                " - current size is - " + ticketQueue.size() +
                                " - Ticket is - " + ticket);
                    }
                    try {
                        Thread.sleep(1000 / customerRetrievalRate);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Consumer finished processing tickets.");
            });
        }
    }

    public synchronized void stopTicketing() {
        isRunning = false;
        producerPool.shutdownNow();
        consumerPool.shutdownNow();
        System.out.println("Ticketing stopped successfully!");
    }
}

