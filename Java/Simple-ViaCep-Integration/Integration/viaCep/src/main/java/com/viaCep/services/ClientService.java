package com.viaCep.services;

import com.viaCep.models.ClientModel;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {

    Iterable<ClientModel> findAll();

    ClientModel findById(Long id);

    void post(ClientModel clientModel);

    void update(Long id, ClientModel clientModel);

    void delete(Long id);
}
