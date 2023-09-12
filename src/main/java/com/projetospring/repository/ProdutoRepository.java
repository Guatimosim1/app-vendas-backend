package com.projetospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospring.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
