package by.mvn.orders.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "T_ORDERS")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Account account;
    private String referUrl;
    private String formName;
    private String phoneNumber;
    private String email;
    private String fullName;
    private String description;
    Order(){ //JPA Only
    }

    public Order(Account account, String referUrl, String formName, String phoneNumber) {
        this.account = account;
        this.referUrl = referUrl;
        this.formName = formName;
        this.phoneNumber = phoneNumber;
    }

    public Order(Account account, String referUrl, String formName, String phoneNumber, String email, String fullName, String description) {
        this.account = account;
        this.referUrl = referUrl;
        this.formName = formName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fullName = fullName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
