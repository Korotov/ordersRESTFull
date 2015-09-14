package by.mvn.orders.repository;

import by.mvn.orders.domains.Account;
import by.mvn.orders.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Collection<Order> findByAccount(Account account);
    Collection<Order> findByAccountUsername(String username);
}
