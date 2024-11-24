package org.project.jwtsecurity_spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.EnumRoleName;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private EnumRoleName roleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
