package org.project.jwtsecurity_spring.repositories;

import org.project.jwtsecurity_spring.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
