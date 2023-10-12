package ticketing_system.app.percistance.repositories.TicketRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.TicketEntities.Ticket;
import ticketing_system.app.percistance.Entities.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket getTicketByTicketId(Long ticketId);
    Ticket getTicketByAgentAssigned(User userId);
}
