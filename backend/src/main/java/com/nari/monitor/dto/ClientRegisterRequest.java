package com.nari.monitor.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ClientRegisterRequest {

    @NotBlank(message = "客户端名称不能为空")
    private String clientName;

    @NotBlank(message = "主机IP不能为空")
    private String hostIp;

    private String hostName;

    private String osType;

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getHostIp() { return hostIp; }
    public void setHostIp(String hostIp) { this.hostIp = hostIp; }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public String getOsType() { return osType; }
    public void setOsType(String osType) { this.osType = osType; }
}
