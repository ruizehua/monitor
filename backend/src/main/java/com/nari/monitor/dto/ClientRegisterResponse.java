package com.nari.monitor.dto;

import java.util.List;

public class ClientRegisterResponse {

    private Long clientId;
    private String clientName;
    private String hostIp;
    private Integer reportInterval;
    private List<String> monitorItems;

    public ClientRegisterResponse() {}

    public ClientRegisterResponse(Long clientId, String clientName, String hostIp, 
                                  Integer reportInterval, List<String> monitorItems) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.hostIp = hostIp;
        this.reportInterval = reportInterval;
        this.monitorItems = monitorItems;
    }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getHostIp() { return hostIp; }
    public void setHostIp(String hostIp) { this.hostIp = hostIp; }

    public Integer getReportInterval() { return reportInterval; }
    public void setReportInterval(Integer reportInterval) { this.reportInterval = reportInterval; }

    public List<String> getMonitorItems() { return monitorItems; }
    public void setMonitorItems(List<String> monitorItems) { this.monitorItems = monitorItems; }
}
