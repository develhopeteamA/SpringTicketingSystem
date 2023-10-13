package ticketing_system.app.percistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
