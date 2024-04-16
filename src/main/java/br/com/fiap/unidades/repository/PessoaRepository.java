package br.com.fiap.unidades.repository;

import br.com.fiap.unidades.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNomeContainsIgnoreCase(String nome);

}
