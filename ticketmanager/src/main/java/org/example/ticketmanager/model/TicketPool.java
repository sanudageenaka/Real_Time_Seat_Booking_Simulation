package org.example.ticketmanager.model;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TicketPool {

    private final List<Integer> ticketPool = new LinkedList<>();
    private  int maxCapacity;

    public TicketPool() {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket() {
        if (ticketPool.size() < maxCapacity) {
            ticketPool.add(1);
            System.out.println(Thread.currentThread().getName() + " added 1 ticket. Current pool size: " + ticketPool.size());
        } else {
            System.out.println(Thread.currentThread().getName() + " cannot add more tickets. Pool is full.");
        }
    }

    public synchronized boolean removeTicket() {
        if (ticketPool.isEmpty()) {
            return false;
        }
        ticketPool.remove(0);
        System.out.println(Thread.currentThread().getName() + " booked a ticket. Remaining tickets: " + ticketPool.size());
        return true;
    }

    public synchronized int getTicketCount() {
        return ticketPool.size();
    }

}
