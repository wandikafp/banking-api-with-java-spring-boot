package dev.wandika.springbootdemo.account.model.entity;

import dev.wandika.springbootdemo.account.model.AccountStatus;
import dev.wandika.springbootdemo.account.model.AccountType;
import dev.wandika.springbootdemo.user.model.entity.UserEntity;
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
@Table(name = "banking_account")
public class BankAccountEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(unique = true)
    private String number;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
