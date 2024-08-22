package com.viaCep.services.impl;

import com.viaCep.models.AddressModel;
import com.viaCep.models.ClientModel;
import com.viaCep.repositories.AddressRepository;
import com.viaCep.repositories.ClientRepository;
import com.viaCep.services.ClientService;
import com.viaCep.services.ViaCepService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ViaCepService viaCepService;

    public ClientServiceImpl(ClientRepository clientRepository, AddressRepository addressRepository, ViaCepService viaCepService) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.viaCepService = viaCepService;
    }

    @Override
    public Iterable<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientModel findById(Long id) {
        Optional<ClientModel> clientModel = clientRepository.findById(id);

        // return clientModel.orElse(null);

        if (clientModel.isEmpty()) return null;

        return clientModel.get();

    }

    @Override
    public void post(ClientModel clientModel) {
        saveClient(clientModel);
    }

    @Override
    public void update(Long id, ClientModel clientModel) {
        Optional<ClientModel> databaseClient = clientRepository.findById(id);

        if (databaseClient.isEmpty()) throw new RuntimeException("Cliente Inexistente");

        saveClient(clientModel);

    }

    private void saveClient(ClientModel clientModel) {
        String cep = clientModel.getAddress().getCep();

        AddressModel address =  addressRepository.findById(cep).orElseGet(() -> {
            AddressModel newAddress = viaCepService.getCep(cep);
            addressRepository.save(newAddress);
            return newAddress;
        });

        clientModel.setAddress(address);

        clientRepository.save(clientModel);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
