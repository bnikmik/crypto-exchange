package com.cryptoexchange.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String login;
    private String fullName;
    private List<Role> rolesList;
    private Boolean isVerified;
    private Boolean isActive;
    private String phoneNumber;
    private String email;
    private URL avatarLink;
    private Map<Currency, UUID> customerAccounts = Collections.emptyMap();
}
