package com.nari.monitor.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MonitorDataRequest {

    @NotNull(message = "客户端ID不能为空")
    private Long clientId;

    private Double cpuUsage;

    private Long physicalMemoryUsed;
    private Long physicalMemoryTotal;
    private Long virtualMemoryUsed;
    private Long virtualMemoryTotal;

    private Double diskUsage;
    private Long diskTotal;
    private Long diskUsed;

    private Integer processCount;
    private String processInfo;

    private LocalDateTime reportTime;

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

    public Integer getProcessCount() { return processCount; }
    public void setProcessCount(Integer processCount) { this.processCount = processCount; }

    public String getProcessInfo() { return processInfo; }
    public void setProcessInfo(String processInfo) { this.processInfo = processInfo; }

    public LocalDateTime getReportTime() { return reportTime; }
    public void setReportTime(LocalDateTime reportTime) { this.reportTime = reportTime; }
}
