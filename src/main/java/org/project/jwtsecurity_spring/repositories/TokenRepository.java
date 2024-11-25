package org.project.jwtsecurity_spring.repositories;

import org.project.jwtsecurity_spring.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsById(String id);
}
