package com.cryptoexchange.customer.model;

import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.common.model.Role;
import lombok.Data;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
public class Customer {
    @Id
    private UUID id;
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
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "currency")
    private Map<Currency, UUID> customerAccounts;
}
