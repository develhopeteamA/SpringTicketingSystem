package ticketing_system.app.percistance.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ticketing_system.app.percistance.Entities.InternalMessage;
import ticketing_system.app.percistance.Entities.User;

import java.util.List;

public interface InternalMessageRepository extends JpaRepository<InternalMessage, Long> {


    List<InternalMessage> findByReceiverOrderByTimestampDesc(User receiver);

    List<InternalMessage> findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampDesc(
            User sender1, User receiver1, User sender2, User receiver2);

    List<InternalMessage> findByReceiverAndReadIsFalse(User receiver);
}
