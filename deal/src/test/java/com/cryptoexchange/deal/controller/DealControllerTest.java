package com.cryptoexchange.deal.controller;

import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.common.model.DealType;
import com.cryptoexchange.deal.dto.DealDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {DealControllerTest.Initializer.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DealControllerTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()

//                    "spring.liquibase.enabled=true",
//                    "spring.jpa.hibernate.ddl-auto=validate",
//                    "spring.liquibase.change-log=classpath:deal/liquibase/changelog.xml"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Container
    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("123")
            .withInitScript("db.sql");

//    @DynamicPropertySource
//    static void postgresqlProperties(DynamicPropertyRegistry registry) {
//        postgreSQLContainer.start();
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KeycloakTokenService keycloakTokenService;

    @Test
    void createNewDeal() throws Exception {

        DealDTO dealDTO = new DealDTO();
        dealDTO.setDealType(DealType.P2P);
        dealDTO.setCurrency(Currency.BTC);
        dealDTO.setBalance(BigDecimal.TEN);
        dealDTO.setSellerId(UUID.fromString("ce217f3b-175e-4019-ba91-22d15daea5b0"));
        dealDTO.setBuyerId(UUID.fromString("4ca46e32-2401-412e-9a2d-deabc375ff45"));
        dealDTO.setGuarantorId(UUID.randomUUID());
        dealDTO.setAuctionId(UUID.randomUUID());

        mockMvc.perform(post("/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dealDTO)).header("Authorization", "Bearer " + keycloakTokenService.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    void findDealById() {
    }

    @Test
    void updateDealById() {
    }
}