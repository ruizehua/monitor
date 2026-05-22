package com.nari.monitor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitor_data")
public class MonitorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "cpu_usage")
    private Double cpuUsage;

    @Column(name = "physical_memory_used")
    private Long physicalMemoryUsed;

    @Column(name = "physical_memory_total")
    private Long physicalMemoryTotal;

    @Column(name = "virtual_memory_used")
    private Long virtualMemoryUsed;

    @Column(name = "virtual_memory_total")
    private Long virtualMemoryTotal;

    @Column(name = "disk_usage")
    private Double diskUsage;

    @Column(name = "disk_total")
    private Long diskTotal;

    @Column(name = "disk_used")
    private Long diskUsed;

    @Column(name = "disk_mount", length = 100)
    private String diskMount;

    @Column(name = "process_count")
    private Integer processCount;

    @Column(name = "process_info", columnDefinition = "TEXT")
    private String processInfo;

    @Column(name = "report_time", nullable = false)
    private LocalDateTime reportTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(Double cpuUsage) { this.cpuUsage = cpuUsage; }

    public Long getPhysicalMemoryUsed() { return physicalMemoryUsed; }
    public void setPhysicalMemoryUsed(Long physicalMemoryUsed) { this.physicalMemoryUsed = physicalMemoryUsed; }

    public Long getPhysicalMemoryTotal() { return physicalMemoryTotal; }
    public void setPhysicalMemoryTotal(Long physicalMemoryTotal) { this.physicalMemoryTotal = physicalMemoryTotal; }

    public Long getVirtualMemoryUsed() { return virtualMemoryUsed; }
    public void setVirtualMemoryUsed(Long virtualMemoryUsed) { this.virtualMemoryUsed = virtualMemoryUsed; }

    public Long getVirtualMemoryTotal() { return virtualMemoryTotal; }
    public void setVirtualMemoryTotal(Long virtualMemoryTotal) { this.virtualMemoryTotal = virtualMemoryTotal; }

    public Double getDiskUsage() { return diskUsage; }
    public void setDiskUsage(Double diskUsage) { this.diskUsage = diskUsage; }

    public Long getDiskTotal() { return diskTotal; }
    public void setDiskTotal(Long diskTotal) { this.diskTotal = diskTotal; }

    public Long getDiskUsed() { return diskUsed; }
    public void setDiskUsed(Long diskUsed) { this.diskUsed = diskUsed; }

    public String getDiskMount() { return diskMount; }
    public void setDiskMount(String diskMount) { this.diskMount = diskMount; }

    public Integer getProcessCount() { return processCount; }
    public void setProcessCount(Integer processCount) { this.processCount = processCount; }

    public String getProcessInfo() { return processInfo; }
    public void setProcessInfo(String processInfo) { this.processInfo = processInfo; }

    public LocalDateTime getReportTime() { return reportTime; }
    public void setReportTime(LocalDateTime reportTime) { this.reportTime = reportTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
