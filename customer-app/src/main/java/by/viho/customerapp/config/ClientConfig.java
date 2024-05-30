package by.viho.customerapp.config;

import by.viho.customerapp.client.WebCLientFeedbackServiceReviewsClient;
import by.viho.customerapp.client.WebClientFeedbackFavouritesClient;
import by.viho.customerapp.client.WebClientItemsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig

{
    @Bean
    @LoadBalanced
    public WebClient.Builder lbWebClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClientItemsClient webClientItemsClient(){
        return new WebClientItemsClient(lbWebClientBuilder().baseUrl("lb://catalogue-service").build());
    }
    @Bean
    public WebCLientFeedbackServiceReviewsClient webClientItemsClientFeedbackServiceReviewsClient(){
        return new WebCLientFeedbackServiceReviewsClient(lbWebClientBuilder().baseUrl("lb://feedback-service").build());
    }
    @Bean
    public WebClientFeedbackFavouritesClient webClientFeedbackFavouritesClient(){
        return new WebClientFeedbackFavouritesClient(lbWebClientBuilder().baseUrl("lb://feedback-service").build());
    }

}
