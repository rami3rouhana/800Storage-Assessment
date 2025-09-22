package com.example.salessystem.controller;

import com.example.salessystem.model.Client;
import com.example.salessystem.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for clients.  The base path is
 * /api/clients.  The service layer handles validation and not found cases.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Return all clients.
     */
    @GetMapping
    public List<Client> getClients() {
        return clientService.findAll();
    }

    /**
     * Create a new client.
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client created = clientService.create(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing client.
     */
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }
}