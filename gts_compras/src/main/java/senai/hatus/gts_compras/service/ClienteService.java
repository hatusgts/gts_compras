package senai.hatus.gts_compras.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senai.hatus.gts_compras.entity.Cliente;
import senai.hatus.gts_compras.repository.ClienteRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso.");
        }
        if (cliente.getEnderecos() != null) {
            cliente.getEnderecos().forEach(e -> e.setCliente(cliente));
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

    @Transactional
    public Cliente update(UUID id, Cliente updatedCliente) {
        Cliente existing = findById(id);
        
        if (!existing.getEmail().equals(updatedCliente.getEmail()) && 
            clienteRepository.existsByEmail(updatedCliente.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso.");
        }
        
        existing.setNome(updatedCliente.getNome());
        existing.setEmail(updatedCliente.getEmail());
        
        return clienteRepository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        Cliente existing = findById(id);
        clienteRepository.delete(existing);
    }
}
