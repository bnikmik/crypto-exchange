package com.cryptoexchange.customer.dto;

import com.cryptoexchange.customer.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.List;

@Getter
@Setter
public class CustomerDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank
    private String login;
    @NotBlank
    private String fullName;
    @NotEmpty
    private List<Role> rolesList;
    private boolean isVerified;
    @NotBlank
    private String phoneNumber;
    @Email
    private String email;
    @NotNull
    private URL avatarLink;
}
