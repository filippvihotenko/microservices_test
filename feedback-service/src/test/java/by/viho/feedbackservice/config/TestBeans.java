package by.viho.feedbackservice.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MongoDBContainer;

@Configuration
public class TestBeans
{
    @Bean
    @ServiceConnection
    public MongoDBContainer mongoDBContainer(){
        return new MongoDBContainer("mongo:7");
    }
}
