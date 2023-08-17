package com.cryptoexchange.common.keycloak;

import com.cryptoexchange.common.dto.CustomerDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class CustomClaimsService {

    public String getLoggedUserId() {
        final DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String userId = "";

        OidcIdToken token = user.getIdToken();

        Map<String, Object> customClaims = token.getClaims();

        if (customClaims.containsKey("sub")) {
            userId = String.valueOf(customClaims.get("sub"));
        }

        return userId;
    }

    public CustomerDTO createCustomer() {
        final DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String userId = "";
        String login = "";
        String fullName = "";
        String email = "";

        OidcIdToken token = user.getIdToken();

        Map<String, Object> customClaims = token.getClaims();

        if (customClaims.containsKey("sub"))
            userId = String.valueOf(customClaims.get("sub"));

        if (customClaims.containsKey("preferred_username"))
            login = String.valueOf(customClaims.get("preferred_username"));

        if (customClaims.containsKey("name"))
            fullName = String.valueOf(customClaims.get("name"));

        if (customClaims.containsKey("email"))
            email = String.valueOf(customClaims.get("email"));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(UUID.fromString(userId));
        customerDTO.setLogin(login);
        customerDTO.setFullName(fullName);
        customerDTO.setEmail(email);

        return customerDTO;
    }
}
