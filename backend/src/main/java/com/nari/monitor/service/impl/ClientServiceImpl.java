package com.nari.monitor.service.impl;

import com.nari.monitor.dto.ClientRegisterRequest;
import com.nari.monitor.dto.ClientRegisterResponse;
import com.nari.monitor.entity.Client;
import com.nari.monitor.exception.BusinessException;
import com.nari.monitor.repository.ClientRepository;
import com.nari.monitor.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Value("${monitor.default-report-interval:30}")
    private Integer defaultReportInterval;

    @Value("${monitor.default-monitor-items:cpu_usage,physical_memory,virtual_memory,disk_usage,process_info}")
    private String defaultMonitorItems;

    @Override
    @Transactional
    public ClientRegisterResponse register(ClientRegisterRequest request) {
        logger.info("Registering client: {}", request.getClientName());

        if (clientRepository.existsByClientName(request.getClientName())) {
            throw new BusinessException(400, "客户端名称已存在");
        }

        Client client = new Client();
        client.setClientName(request.getClientName());
        client.setHostIp(request.getHostIp());
        client.setHostName(request.getHostName());
        client.setOsType(request.getOsType());
        client.setReportInterval(defaultReportInterval);
        client.setIsEnabled(1);
        client.setIsDeleted(0);

        Client savedClient = clientRepository.save(client);

        List<String> monitorItems = Arrays.asList(defaultMonitorItems.split(","));

        logger.info("Client registered successfully: id={}, name={}", savedClient.getId(), savedClient.getClientName());

        return new ClientRegisterResponse(
                savedClient.getId(),
                savedClient.getClientName(),
                savedClient.getHostIp(),
                savedClient.getReportInterval(),
                monitorItems
        );
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findByIdAndIsDeleted(id, 0)
                .orElseThrow(() -> new BusinessException(404, "客户端不存在"));
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findByIsDeleted(0);
    }

    @Override
    @Transactional
    public Client updateClient(Long id, ClientRegisterRequest request) {
        Client client = getClientById(id);

        if (!client.getClientName().equals(request.getClientName()) 
                && clientRepository.existsByClientName(request.getClientName())) {
            throw new BusinessException(400, "客户端名称已存在");
        }

        client.setClientName(request.getClientName());
        client.setHostIp(request.getHostIp());
        client.setHostName(request.getHostName());
        client.setOsType(request.getOsType());

        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        Client client = getClientById(id);
        client.setIsDeleted(1);
        clientRepository.save(client);
    }
}
