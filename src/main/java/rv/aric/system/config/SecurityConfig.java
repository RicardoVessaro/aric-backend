package rv.aric.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(configurer -> {
            configurer.requestMatchers(HttpMethod.GET, "/system/**").permitAll();
        });

        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }

}
