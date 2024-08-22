package com.viaCep.controllers;


import com.viaCep.models.ClientModel;
import com.viaCep.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Iterable<ClientModel>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientModel> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientModel> post(@RequestBody ClientModel clientModel) {
        clientService.post(clientModel);
        return ResponseEntity.ok(clientModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientModel> update(@PathVariable Long id, @RequestBody ClientModel clientModel) {
        clientService.update(id, clientModel);
        return ResponseEntity.ok(clientModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Client deleted succesfully");
    }

}
