package com.nari.monitor.service;

import com.nari.monitor.dto.ClientRegisterRequest;
import com.nari.monitor.dto.ClientRegisterResponse;
import com.nari.monitor.entity.Client;

import java.util.List;

public interface ClientService {

    ClientRegisterResponse register(ClientRegisterRequest request);

    Client getClientById(Long id);

    List<Client> getAllClients();

    Client updateClient(Long id, ClientRegisterRequest request);

    void deleteClient(Long id);
}
