package com.projetospring.services;

import java.util.ArrayList;
import java.util.List;

import com.projetospring.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetospring.dto.ClienteDTO;
import com.projetospring.models.Cliente;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}
	
	public List<ClienteDTO> listarClientesDTO() {
		List<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
		
		clienteRepository.findAll().forEach(c -> 
			clientes.add(new ClienteDTO(c.getCpf(), c.getNome(), c.getTelefone(), c.getEmail())));
		
		return clientes;
	}
	
	public ClienteDTO buscarCliente(String cpf) {
		Cliente cliente = clienteRepository.getReferenceById(cpf);
		return new ClienteDTO(
				cliente.getCpf(), 
				cliente.getNome(), 
				cliente.getTelefone(), 
				cliente.getEmail());
	}
	
	
	public Cliente incluir(Cliente cliente) {
		return clienteRepository.save(cliente);
		
	}
	
	public Cliente alterar(Cliente cliente, String cpf) {
//		Cliente c = clienteRepository.getReferenceById(cpf);
//		if(c == null) {
//			throw new EntityNotFoundException("O Cliente a ser alterado não existe.");
//		}
		
		cliente.setCpf(cpf);
		return clienteRepository.save(cliente);
		
	}
	
	public String remover(String cpf) {
		try {
			clienteRepository.deleteById(cpf);
			return String.format("Cliente %s removido com sucesso!", cpf); 
		} catch (Exception e) {
			return e.toString();
		}
		
	}
}





