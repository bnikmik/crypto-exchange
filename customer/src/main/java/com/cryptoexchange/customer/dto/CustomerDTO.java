package com.cryptoexchange.customer.dto;

import com.cryptoexchange.common.model.Role;
import com.cryptoexchange.common.validator.ValidURL;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.net.URL;
import java.util.*;

@Getter
@Setter
public class CustomerDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank(message = "Login не может быть пустым")
    private String login;
    @NotBlank(message = "Full name не может быть пустым")
    private String fullName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Role> rolesList = new ArrayList<>() {{
        add(Role.PHYSICAL);
        add(Role.USER);
    }};
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isVerified = false;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive = true;
    @Pattern(regexp = "^[0-9]{10,12}$", message = "Номер телефона должен состоять из 10 до 12 цифр")
    private String phoneNumber;
    @Email(message = "Email должен быть корректным: name@email.com")
    @NotBlank(message = "Email не может быть пустым")
    private String email;
    @ValidURL(message = "URL должен соответстовать типу http://url.com")
    private URL avatarLink;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Map<Currency, UUID> customerAccounts = Collections.emptyMap();
}
