package com.cryptoexchange.customer.model;

import lombok.Data;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String fullName;
    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "customer_roles", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "role")
    private List<Role> rolesList;
    private Boolean isVerified;
    private Boolean isActive;
    private String phoneNumber;
    private String email;
    private URL avatarLink;
    @ElementCollection
    @CollectionTable(name = "customer_accounts", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "account")
    private List<UUID> accountsList;
}
