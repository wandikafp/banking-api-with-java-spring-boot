package dev.wandika.springbootdemo.transaction.repository;

import dev.wandika.springbootdemo.transaction.model.entity.FundTransferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FundTransferRepository extends JpaRepository<FundTransferEntity, UUID> {
    @Query(value = "SELECT * FROM user",
            countQuery = "SELECT count(*) FROM user",
            nativeQuery = true)
    Page<FundTransferEntity> findAll(Pageable pageable);
}
