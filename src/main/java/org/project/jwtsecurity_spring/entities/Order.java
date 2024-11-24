package org.project.jwtsecurity_spring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.EnumStatusOrder;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double totalPrice;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private EnumStatusOrder status;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
