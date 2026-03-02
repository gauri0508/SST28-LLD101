import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Starter demo that shows why mutability is risky.
 *
 * After refactor:
 * - direct mutation should not compile (no setters)
 * - external modifications to tags should not affect the ticket
 * - service "updates" should return a NEW ticket instance
 */
public class TryIt {

    // public static void main(String[] args) {
    //     TicketService service = new TicketService();

    //     IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
    //     System.out.println("Created: " + t);

    //     // Demonstrate post-creation mutation through service
    //     service.assign(t, "agent@example.com");
    //     service.escalateToCritical(t);
    //     System.out.println("\nAfter service mutations: " + t);

    //     // Demonstrate external mutation via leaked list reference
    //     List<String> tags = t.getTags();
    //     tags.add("HACKED_FROM_OUTSIDE");
    //     System.out.println("\nAfter external tag mutation: " + t);

    //     // Starter compiles; after refactor, you should redesign updates to create new objects instead.
    // }

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // "Update" creates a NEW ticket — original t is unchanged
        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nOriginal after updates: " + t);
        System.out.println("Assigned copy         : " + assigned);
        System.out.println("Escalated copy        : " + escalated);

        // External tag mutation has no effect — getTags() returns an immutable list
        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked: " + e);
        }
        System.out.println("Tags still safe: " + escalated.getTags());
    }
}
