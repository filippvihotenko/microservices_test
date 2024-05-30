package by.viho.managerapp.config;

import by.viho.managerapp.client.RestClientItemsRestClient;
import by.viho.managerapp.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientItemsRestClient productsRestClient(
            @Value("${itemservice.service.catalogue.uri:http://localhost:8081}") String catalogueBaseUri, ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository, LoadBalancerClient loadBalancerClient)
    {
        return new RestClientItemsRestClient(RestClient.builder().baseUrl(catalogueBaseUri).requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(new DefaultOAuth2AuthorizedClientManager
                        (clientRegistrationRepository, oAuth2AuthorizedClientRepository), "keycloak") ).build());
    }
}