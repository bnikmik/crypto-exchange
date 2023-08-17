package com.cryptoexchange.common.dto;

import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.common.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class CustomerDTO {
    private UUID id;
    private String login;
    private String fullName;
    private List<Role> rolesList;
    private Boolean isVerified;
    private Boolean isActive;
    private String phoneNumber;
    private String email;
    private URL avatarLink;
    private Map<Currency, UUID> customerAccounts;
}
