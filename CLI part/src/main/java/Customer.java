public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        try {
            // Attempt to book a ticket
            if (ticketPool.removeTicket()) {
                System.out.println(Thread.currentThread().getName() + " successfully booked a ticket.");
                Thread.sleep(customerRetrievalRate * 1000); // Simulate retrieval rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
