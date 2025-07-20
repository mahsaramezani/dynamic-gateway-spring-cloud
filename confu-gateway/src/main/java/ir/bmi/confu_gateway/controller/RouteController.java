package ir.bmi.confu_gateway.controller;

import ir.bmi.confu_gateway.entity.RouteEntity;
import ir.bmi.confu_gateway.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confugw/routes")
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public List<RouteEntity> getAll() {
        return routeService.getAll();
    }

    @PostMapping
    public RouteEntity create(@RequestBody RouteEntity route) {
        return routeService.create(route);
    }

    @PutMapping("/{id}")
    public RouteEntity update(@PathVariable Long id, @RequestBody RouteEntity updated) {
        return routeService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        routeService.delete(id);
    }
}
