package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void addClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.getReferenceById(id);
    }

    @Override
    public int getDiscountPoints(Long id) {
        return clientRepository.getReferenceById(id).getDiscountPoints();
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }
}
