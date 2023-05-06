package dev.wandika.springbootdemo.transaction.repository;

import dev.wandika.springbootdemo.transaction.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    @Query(value = "SELECT * " +
            "FROM banking_transaction bt " +
            "JOIN banking_account ba ON bt.account_id=ba.id " +
            "WHERE ba.number= :account_number AND bt.created_at >= :fromDate AND bt.created_at <= :toDate " +
            "ORDER BY bt.created_at DESC", nativeQuery = true)
    List<TransactionEntity> findAllByDate(@Param("account_number") String accountNumber,
                                  @Param("fromDate") LocalDateTime fromDate,
                                  @Param("toDate") LocalDateTime toDate);
}
