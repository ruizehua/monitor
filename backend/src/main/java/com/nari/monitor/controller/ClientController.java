package com.nari.monitor.controller;

import com.nari.monitor.dto.ApiResponse;
import com.nari.monitor.dto.ClientRegisterRequest;
import com.nari.monitor.dto.ClientRegisterResponse;
import com.nari.monitor.entity.Client;
import com.nari.monitor.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ApiResponse<ClientRegisterResponse> register(@Valid @RequestBody ClientRegisterRequest request) {
        logger.info("Client register request: {}", request.getClientName());
        ClientRegisterResponse response = clientService.register(request);
        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<Client> getClient(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ApiResponse.success(client);
    }

    @GetMapping("/list")
    public ApiResponse<List<Client>> listClients() {
        List<Client> clients = clientService.getAllClients();
        return ApiResponse.success(clients);
    }

    @PutMapping("/{id}")
    public ApiResponse<Client> updateClient(@PathVariable Long id, 
                                            @Valid @RequestBody ClientRegisterRequest request) {
        Client client = clientService.updateClient(id, request);
        return ApiResponse.success(client);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ApiResponse.success(null);
    }
}
