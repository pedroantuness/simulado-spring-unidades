package br.com.fiap.unidades.repository;

import br.com.fiap.unidades.entity.Chefe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefeRepository extends JpaRepository<Chefe, Long> {
}
