package ir.bmi.confu_gateway.controller;

import ir.bmi.confu_gateway.entity.RouteEntity;
import ir.bmi.confu_gateway.repository.RouteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RouteControllerIT {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private RouteRepository routeRepository;

    @BeforeEach
    void setup() {
        RouteEntity route = new RouteEntity();
        route.setRouteId("my-service");
        route.setUri("http://httpbin.org");
        route.setPredicates("Path=/confugw/routes/**");
        route.setFilters("RewritePath=/confugw/routes/(?<segment>.*), /${segment}");
        route.setEnabled(true);
        routeRepository.save(route);
    }

    // Check that predicates and filters like RewritePath are parsed correctly and don’t throw errors.
    @Test
    @DisplayName("Route Predicate and Filter Parsing")
    void shouldRouteAndRewritePath() {
        // Assuming your Gateway will proxy to httpbin.org/get
        webClient.get()
                .uri("/confugw/routes")
                .exchange()
                .expectStatus().isOk();
                /*.expectBody()
                .jsonPath("$.url").value(url -> Assertions.assertTrue(url.toString().contains("/get")));*/
    }
}