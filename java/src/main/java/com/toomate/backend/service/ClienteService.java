package com.toomate.backend.service;

import com.toomate.backend.dto.cliente.ClienteRequestDto;
import com.toomate.backend.dto.cliente.ClientesResponseDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.mapper.cliente.ClienteMapper;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrar(ClienteRequestDto cliente){

        return clienteRepository.save(ClienteMapper.toEntity(cliente));
    }

    public List<ClientesResponseDto> listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteMapper::toResponses)
                .toList();

    }

    public Optional<Cliente> buscarPorId(Integer idCliente) {
        return clienteRepository.findById(idCliente);
    }

    public Cliente atualizar(Integer idCliente, Cliente cliente){
        cliente.setIdCliente(idCliente);

        if (clienteRepository.existsById(idCliente)){
            return clienteRepository.save(cliente);
        }

        throw new EntidadeNaoEncontradaException("Cliente não encontrado");
    }

    public void deletarPorId(Integer idCliente){
        if (clienteRepository.existsById(idCliente)){
            clienteRepository.deleteById(idCliente);
            return;
        }
        throw new EntidadeNaoEncontradaException("Cliente não encontrado;");
    }

    public List<ClientesResponseDto> buscarPorNome(String nome){
        return clienteRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(ClienteMapper::toResponses)
                .toList();
    }
}
