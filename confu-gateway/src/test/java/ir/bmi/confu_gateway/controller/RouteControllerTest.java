package ir.bmi.confu_gateway.controller;

import ir.bmi.confu_gateway.entity.RouteEntity;
import ir.bmi.confu_gateway.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RouteControllerTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteDefinitionLocator routeLocator;

    @BeforeEach
    void setup() {
        RouteEntity route = new RouteEntity();
        route.setRouteId("test-route");
        route.setUri("http://localhost:8081");
        route.setPredicates("Path=/test/**");
        route.setFilters("RewritePath=/test/(?<segment>.*), /${segment}");
        route.setEnabled(true);
        routeRepository.save(route);
    }

    @Test
    void shouldLoadRouteFromDatabase() {
        List<RouteDefinition> routes = routeLocator.getRouteDefinitions().collectList().block();
        assertNotNull(routes);
        assertEquals(1, routes.size());
        assertEquals("test-route", routes.getFirst().getId());
    }

}