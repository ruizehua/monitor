package com.nari.monitor.repository;

import com.nari.monitor.entity.MonitorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitorDataRepository extends JpaRepository<MonitorData, Long> {

    Page<MonitorData> findByClientId(Long clientId, Pageable pageable);

    Page<MonitorData> findByClientIdAndReportTimeBetween(Long clientId, LocalDateTime startTime, 
                                                          LocalDateTime endTime, Pageable pageable);

    Optional<MonitorData> findTopByClientIdOrderByReportTimeDesc(Long clientId);

    List<MonitorData> findByClientIdAndReportTimeBetween(Long clientId, LocalDateTime startTime, 
                                                          LocalDateTime endTime);

    @Query("SELECT m FROM MonitorData m WHERE m.clientId = :clientId ORDER BY m.reportTime DESC")
    Page<MonitorData> findLatestByClientId(@Param("clientId") Long clientId, Pageable pageable);

    Long countByClientId(Long clientId);
}
