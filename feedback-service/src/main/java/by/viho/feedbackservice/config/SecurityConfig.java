/*

package by.viho.feedbackservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
public class SecurityConfig
{
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity){
        return httpSecurity.build();
    }

}

*/
/*authorizeExchange(customizer -> customizer.anyExchange()
                        .authenticated()).csrf(ServerHttpSecurity.CsrfSpec :: disable).securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()))*/
