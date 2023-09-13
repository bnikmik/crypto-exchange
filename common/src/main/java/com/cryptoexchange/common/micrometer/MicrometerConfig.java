package com.cryptoexchange.common.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {

    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry(); // Вы можете выбрать другую реализацию MeterRegistry по вашему усмотрению.
    }
}