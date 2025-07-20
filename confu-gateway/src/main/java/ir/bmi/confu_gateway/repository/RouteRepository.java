package ir.bmi.confu_gateway.repository;

import ir.bmi.confu_gateway.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {

    List<RouteEntity> findByEnabledTrue();
}
