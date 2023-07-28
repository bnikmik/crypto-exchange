package com.cryptoexchange.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.net.URL;
import java.util.List;

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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isVerified;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive;
    private String phoneNumber;
    private String email;
    private URL avatarLink;
}
