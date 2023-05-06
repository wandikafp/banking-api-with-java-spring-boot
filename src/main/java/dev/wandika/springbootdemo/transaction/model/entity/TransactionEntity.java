package dev.wandika.springbootdemo.transaction.model.entity;

import dev.wandika.springbootdemo.account.model.entity.BankAccountEntity;
import dev.wandika.springbootdemo.transaction.model.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "banking_transaction",
    indexes = {
        @Index(name = "IDX_CREATED_AT", columnList = "createdAt")
    })
public class TransactionEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private String referenceNumber;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private BankAccountEntity account;

}
