package ir.bmi.confu_gateway.service.impl;

import ir.bmi.confu_gateway.entity.RouteEntity;
import ir.bmi.confu_gateway.repository.RouteRepository;
import ir.bmi.confu_gateway.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<RouteEntity> getAll() {

        //todo implement Redis cache
        return routeRepository.findAll();
    }

    @Override
    public RouteEntity create(RouteEntity route) {
        RouteEntity routeEntity = routeRepository.save(route);
        // spring will not re-call getRouteDefinitions until the next app restart, so via this API, routes have manually refreshed
        refresh();

        return routeEntity;
    }

    private void refresh() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        log.info("PWA Routes refreshed successfully :)");
    }

    @Override
    public RouteEntity update(Long id, RouteEntity route) {
        RouteEntity existing = routeRepository.findById(id).orElseThrow();
        existing.setRouteId(route.getRouteId());
        existing.setUri(route.getUri());
        existing.setPredicates(route.getPredicates());
        existing.setFilters(route.getFilters());
        existing.setEnabled(route.getEnabled());
        RouteEntity routeEntity = routeRepository.save(existing);
        refresh();

        return routeEntity;
    }

    @Override
    public void delete(Long id) {
        routeRepository.deleteById(id);
        refresh();
    }
}
