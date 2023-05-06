package dev.wandika.springbootdemo.account.repository;

import dev.wandika.springbootdemo.account.model.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {
    @Query(value="SELECT * FROM banking_account WHERE number=:number LIMIT 1", nativeQuery = true)
    Optional<BankAccountEntity> findByNumber(@Param("number")String accountNumber);

}
