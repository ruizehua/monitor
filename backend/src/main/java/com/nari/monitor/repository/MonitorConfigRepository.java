package com.nari.monitor.repository;

import com.nari.monitor.entity.MonitorConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonitorConfigRepository extends JpaRepository<MonitorConfig, Long> {

    List<MonitorConfig> findByClientId(Long clientId);

    List<MonitorConfig> findByClientIdAndIsDeleted(Long clientId, Integer isDeleted);

    Optional<MonitorConfig> findByClientIdAndConfigKey(Long clientId, String configKey);

    Optional<MonitorConfig> findByClientIdAndConfigKeyAndIsDeleted(Long clientId, String configKey, Integer isDeleted);
}
