package org.project.jwtsecurity_spring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.EnumTokenType;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tokens")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Token {

    @Id
    private String id;
    private Date expires;
    @Enumerated(EnumType.STRING)
    private EnumTokenType type;

}
