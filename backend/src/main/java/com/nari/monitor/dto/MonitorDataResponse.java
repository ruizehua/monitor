package com.nari.monitor.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;

public class MonitorDataResponse {

    private Long id;
    private Long clientId;
    private Double cpuUsage;
    private Long physicalMemoryUsed;
    private Long physicalMemoryTotal;
    private Long virtualMemoryUsed;
    private Long virtualMemoryTotal;
    private Double diskUsage;
    private Long diskTotal;
    private Long diskUsed;
    private String diskMount;
    private Integer processCount;
    private String processInfo;
    private JsonNode processList;
    private LocalDateTime reportTime;
    private LocalDateTime createdAt;

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

    public JsonNode getProcessList() { return processList; }
    public void setProcessList(JsonNode processList) { this.processList = processList; }

    public LocalDateTime getReportTime() { return reportTime; }
    public void setReportTime(LocalDateTime reportTime) { this.reportTime = reportTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
