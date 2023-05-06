package dev.wandika.springbootdemo.user.repository;

import dev.wandika.springbootdemo.user.model.dto.User;
import dev.wandika.springbootdemo.user.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query(value = "SELECT * FROM user where is_deleted=false",
        countQuery = "SELECT count(*) FROM user",
        nativeQuery = true)
    Page<UserEntity> findAll(Pageable pageable);

    @Query(value="SELECT * FROM user WHERE id=:id and is_deleted=false LIMIT 1", nativeQuery = true)
    Optional<UserEntity> findById(@Param("id") String userId);

    @Query(value="SELECT DISTINCT u.* FROM user u join banking_account ba on u.id = ba.user_id " +
            "WHERE ba.balance >= :fromBalance AND ba.balance <= :toBalance", nativeQuery = true)
    List<UserEntity> findByBalance(@Param("fromBalance") BigDecimal fromBalance,
                             @Param("toBalance") BigDecimal toBalance);

}
