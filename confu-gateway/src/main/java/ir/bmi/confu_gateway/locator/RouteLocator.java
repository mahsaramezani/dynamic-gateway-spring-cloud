package ir.bmi.confu_gateway.locator;

import ir.bmi.confu_gateway.entity.RouteEntity;
import ir.bmi.confu_gateway.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteLocator implements RouteDefinitionLocator {

    private final RouteRepository routeRepository;

    /*
     This method is called by spring-cloud-gateway internally and automatically at application startup
     */

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteEntity> routes = routeRepository.findByEnabledTrue();
        List<RouteDefinition> definitions = new ArrayList<>();

        for (RouteEntity entity : routes) {
            RouteDefinition def = new RouteDefinition();
            def.setId(entity.getRouteId());
            def.setUri(URI.create(entity.getUri()));

            // Assume comma-separated predicates like "Path=/service/**"
            List<PredicateDefinition> predicateDefinitions = Arrays.stream(entity.getPredicates().split(","))
                    .map(PredicateDefinition::new)
                    .toList();

            def.setPredicates(predicateDefinitions);

            // Optional filters
            if (entity.getFilters() != null && !entity.getFilters().isEmpty()) {
                List<FilterDefinition> filterDefinitions = Arrays.stream(entity.getFilters().split(";"))
                        .map(FilterDefinition::new)
                        .toList();
                def.setFilters(filterDefinitions);
            }

            definitions.add(def);
        }

        return Flux.fromIterable(definitions);
    }
}