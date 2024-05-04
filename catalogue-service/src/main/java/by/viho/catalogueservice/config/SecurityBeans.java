package by.viho.catalogueservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans
{
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/catalogue-api/items/create")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.PATCH, "/catalogue-api/item/{itemId:\\d}/edit")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.DELETE, "/catalogue-api/item/{itemId:\\d}/delete")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.GET,"/catalogue-api/items/**")
                        .hasAuthority("SCOPE_view_catalogue")
                        .anyRequest().denyAll()).csrf(CsrfConfigurer::disable).sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer->oauth2ResourceServer.jwt(Customizer.withDefaults())).build();
    }
}
