package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.*;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}
