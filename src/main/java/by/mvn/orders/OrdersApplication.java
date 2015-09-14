package by.mvn.orders;

import by.mvn.orders.domains.Account;
import by.mvn.orders.domains.Order;
import by.mvn.orders.repository.AccountRepository;
import by.mvn.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class OrdersApplication {
    @Bean
    CommandLineRunner init(AccountRepository accountRepository, OrderRepository orderRepository){
        return (evt) -> Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(",")).
                forEach(
                        a -> {
                            Account account = accountRepository.save(new Account(a, "qwe123"));
                            Integer phone = new Random(59).nextInt(99999);
                            orderRepository.save(new Order(account, "http://mvn.by", "First Form", phone.toString()));
                        }
                );
    }

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
}

@RestController
@RequestMapping("/{userId}/orders")
class OrderRestController {

    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Order input){
        this.validateUser(userId);
        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Order result = orderRepository.save(new Order(account, input.getReferUrl(), input.getFormName(), input.getPhoneNumber()));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }
    @RequestMapping(value = "/orderId", method = RequestMethod.GET)
    Order getOrder(@PathVariable String userId, @PathVariable long orderId) {
        this.validateUser(userId);
        return this.orderRepository.findOne(orderId);
    }
    @RequestMapping(method = RequestMethod.GET)
    Collection<Order> getOrders(@PathVariable String userId){
        this.validateUser(userId);
        return this.orderRepository.findByAccountUsername(userId);
    }
    @Autowired
    OrderRestController(OrderRepository orderRepository, AccountRepository accountRepository){
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
    }
    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}
