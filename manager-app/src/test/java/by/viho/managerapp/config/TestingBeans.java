package by.viho.managerapp.config;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

@Configuration
public class TestingBeans
{
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        return Mockito.mock(ClientRegistrationRepository.class);
    }
    @Bean
    public OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository(){
        return Mockito.mock(OAuth2AuthorizedClientRepository.class);
    }

}

