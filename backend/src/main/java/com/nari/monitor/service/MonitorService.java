package com.nari.monitor.service;

import com.nari.monitor.dto.MonitorDataQueryRequest;
import com.nari.monitor.dto.MonitorDataRequest;
import com.nari.monitor.dto.MonitorDataResponse;
import com.nari.monitor.dto.PageResponse;

public interface MonitorService {

    void reportMonitorData(MonitorDataRequest request);

    MonitorDataResponse getLatestData(Long clientId);

    PageResponse<MonitorDataResponse> queryMonitorData(MonitorDataQueryRequest request);
}
