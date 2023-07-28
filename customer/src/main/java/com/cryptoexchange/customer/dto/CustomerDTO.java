package com.cryptoexchange.customer.dto;

import com.cryptoexchange.customer.model.Role;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isVerified = false;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive = true;
    @NotBlank
    private String phoneNumber;
    @Email
    private String email;
    @NotNull
    private URL avatarLink;
}
