package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

//    @Bean
//    public ApplicationRunner printRoutes(RouteLocator routeLocator) {
//        return args -> routeLocator.getRoutes()
//                .subscribe(route -> System.out.println("🔗 Route Loaded: " + route.getId() + " → " + route.getUri()));
//    }


}
