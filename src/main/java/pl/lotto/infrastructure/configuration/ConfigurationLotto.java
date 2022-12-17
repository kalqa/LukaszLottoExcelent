package pl.lotto.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ConfigurationLotto {
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}