package com.nari.monitor.service.impl;

import com.nari.monitor.dto.MonitorDataQueryRequest;
import com.nari.monitor.dto.MonitorDataRequest;
import com.nari.monitor.dto.MonitorDataResponse;
import com.nari.monitor.dto.PageResponse;
import com.nari.monitor.entity.Client;
import com.nari.monitor.entity.MonitorData;
import com.nari.monitor.exception.BusinessException;
import com.nari.monitor.repository.ClientRepository;
import com.nari.monitor.repository.MonitorDataRepository;
import com.nari.monitor.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitorServiceImpl implements MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @Autowired
    private MonitorDataRepository monitorDataRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public void reportMonitorData(MonitorDataRequest request) {
        logger.debug("Received monitor data from client: {}", request.getClientId());

        Client client = clientRepository.findByIdAndIsDeleted(request.getClientId(), 0)
                .orElseThrow(() -> new BusinessException(404, "客户端不存在"));

        MonitorData data = new MonitorData();
        data.setClientId(request.getClientId());
        data.setCpuUsage(request.getCpuUsage());
        data.setPhysicalMemoryUsed(request.getPhysicalMemoryUsed());
        data.setPhysicalMemoryTotal(request.getPhysicalMemoryTotal());
        data.setVirtualMemoryUsed(request.getVirtualMemoryUsed());
        data.setVirtualMemoryTotal(request.getVirtualMemoryTotal());
        data.setDiskUsage(request.getDiskUsage());
        data.setDiskTotal(request.getDiskTotal());
        data.setDiskUsed(request.getDiskUsed());
        data.setProcessCount(request.getProcessCount());
        data.setProcessInfo(request.getProcessInfo());
        data.setReportTime(request.getReportTime() != null ? request.getReportTime() : LocalDateTime.now());

        monitorDataRepository.save(data);

        logger.debug("Monitor data saved successfully for client: {}", request.getClientId());
    }

    @Override
    public MonitorDataResponse getLatestData(Long clientId) {
        Client client = clientRepository.findByIdAndIsDeleted(clientId, 0)
                .orElseThrow(() -> new BusinessException(404, "客户端不存在"));

        MonitorData data = monitorDataRepository.findTopByClientIdOrderByReportTimeDesc(clientId)
                .orElse(null);

        if (data == null) {
            return null;
        }

        return convertToResponse(data);
    }

    @Override
    public PageResponse<MonitorDataResponse> queryMonitorData(MonitorDataQueryRequest request) {
        Client client = clientRepository.findByIdAndIsDeleted(request.getClientId(), 0)
                .orElseThrow(() -> new BusinessException(404, "客户端不存在"));

        Pageable pageable = PageRequest.of(
                request.getPage(), 
                request.getSize(), 
                Sort.by(Sort.Direction.DESC, "reportTime")
        );

        Page<MonitorData> page;

        if (request.getStartTime() != null && request.getEndTime() != null) {
            page = monitorDataRepository.findByClientIdAndReportTimeBetween(
                    request.getClientId(),
                    request.getStartTime(),
                    request.getEndTime(),
                    pageable
            );
        } else {
            page = monitorDataRepository.findByClientId(request.getClientId(), pageable);
        }

        List<MonitorDataResponse> content = page.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private MonitorDataResponse convertToResponse(MonitorData data) {
        MonitorDataResponse response = new MonitorDataResponse();
        response.setId(data.getId());
        response.setClientId(data.getClientId());
        response.setCpuUsage(data.getCpuUsage());
        response.setPhysicalMemoryUsed(data.getPhysicalMemoryUsed());
        response.setPhysicalMemoryTotal(data.getPhysicalMemoryTotal());
        response.setVirtualMemoryUsed(data.getVirtualMemoryUsed());
        response.setVirtualMemoryTotal(data.getVirtualMemoryTotal());
        response.setDiskUsage(data.getDiskUsage());
        response.setDiskTotal(data.getDiskTotal());
        response.setDiskUsed(data.getDiskUsed());
        response.setProcessCount(data.getProcessCount());
        response.setProcessInfo(data.getProcessInfo());
        response.setReportTime(data.getReportTime());
        response.setCreatedAt(data.getCreatedAt());
        return response;
    }
}
