package com.wannago.repository;

import com.wannago.entity.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertsRepository extends JpaRepository<Alerts, Integer> {
}
