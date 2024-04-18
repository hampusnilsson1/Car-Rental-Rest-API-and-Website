package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Repositories;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContractRepository extends JpaRepository <Contract, Long> {

    Contract findByContractNumber(long id);

    @Query(value = "SELECT * FROM Contract c WHERE c.email = ?1 AND c.expired = false", nativeQuery = true)
    List<Contract> findByUserEmail(String email);

    @Query(value = "SELECT * FROM Contract c WHERE c.license_plate = ?1 AND c.expired = false", nativeQuery = true)
    List<Contract> findByLicensePlate(String license_plate);
}
