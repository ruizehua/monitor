package com.nari.monitor.controller;

import com.nari.monitor.dto.ApiResponse;
import com.nari.monitor.dto.MonitorDataQueryRequest;
import com.nari.monitor.dto.MonitorDataRequest;
import com.nari.monitor.dto.MonitorDataResponse;
import com.nari.monitor.dto.PageResponse;
import com.nari.monitor.service.MonitorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/monitor")
public class MonitorController {

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private MonitorService monitorService;

    @PostMapping("/report")
    public ApiResponse<Void> report(@Valid @RequestBody MonitorDataRequest request) {
        logger.debug("Monitor data report from client: {}", request.getClientId());
        monitorService.reportMonitorData(request);
        return ApiResponse.success(null);
    }

    @GetMapping("/{clientId}/latest")
    public ApiResponse<MonitorDataResponse> getLatest(@PathVariable Long clientId) {
        MonitorDataResponse data = monitorService.getLatestData(clientId);
        return ApiResponse.success(data);
    }

    @GetMapping("/{clientId}")
    public ApiResponse<PageResponse<MonitorDataResponse>> query(
            @PathVariable Long clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        MonitorDataQueryRequest request = new MonitorDataQueryRequest();
        request.setClientId(clientId);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setPage(page);
        request.setSize(size);

        PageResponse<MonitorDataResponse> response = monitorService.queryMonitorData(request);
        return ApiResponse.success(response);
    }
}
