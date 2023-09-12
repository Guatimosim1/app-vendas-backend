package com.projetospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospring.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{
	
}
