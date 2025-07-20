package ir.bmi.confu_gateway.service;

import ir.bmi.confu_gateway.entity.RouteEntity;

import java.util.List;

public interface RouteService {

    List<RouteEntity> getAll();

    RouteEntity create(RouteEntity route);

    RouteEntity update(Long id, RouteEntity route);

    void delete(Long id);
}
