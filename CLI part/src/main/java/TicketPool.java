import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    private List<Integer> ticketPool;
    private int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.ticketPool = new LinkedList<>();
    }

    // Method to add tickets to the pool (vendor action)
    public synchronized void addTicket() {
        // Only add ticket if the pool has space
        if (ticketPool.size() < maxCapacity) {
            ticketPool.add(1);  // Simulating a ticket being added
            System.out.println(Thread.currentThread().getName() + " added 1 ticket. Current pool size: " + ticketPool.size());
        } else {
            System.out.println(Thread.currentThread().getName() + " cannot add more tickets. Pool is full.");
        }
    }

    // Method to remove a ticket from the pool (customer action)
    public synchronized boolean removeTicket() {
        if (ticketPool.isEmpty()) {

            return false; // No tickets available to remove
        }
        ticketPool.remove(0); // Simulating a ticket being sold
        System.out.println(Thread.currentThread().getName() + " booked a ticket. Remaining tickets: " + ticketPool.size());
        return true;
    }

    public synchronized int getTicketCount() {
        return ticketPool.size();
    }
}
