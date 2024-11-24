package org.project.jwtsecurity_spring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.EnumPayMentStatus;
import org.project.jwtsecurity_spring.dtos.enums.EnumPaymentMethod;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private EnumPaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private EnumPayMentStatus status;
    private String transactionId;
    private double amount;
    private LocalDateTime paymentDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
}
