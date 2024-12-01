package org.project.jwtsecurity_spring.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String avatar;
    private String googleAccountId;
    private String facebookAccountId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

}
