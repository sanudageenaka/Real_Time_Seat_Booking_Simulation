import java.util.concurrent.CountDownLatch;

public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int ticketReleaseRate;
    private int totalTickets;
    private CountDownLatch latch;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int totalTickets, CountDownLatch latch) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalTickets; i++) {
                ticketPool.addTicket();  // Correct method call
                Thread.sleep(ticketReleaseRate * 1000); // Simulate release rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown();  // Signal that vendor finished adding tickets
            System.out.println(Thread.currentThread().getName() + " has finished adding tickets.");
        }
    }
}
