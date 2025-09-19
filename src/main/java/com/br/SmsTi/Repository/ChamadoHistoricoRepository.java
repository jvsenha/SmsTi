package com.br.SmsTi.Repository;

import com.br.SmsTi.Entity.ChamadoEntity;
import com.br.SmsTi.Entity.ChamadoHistoricoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoHistoricoRepository extends JpaRepository<ChamadoHistoricoEntity, Long> {
    List<ChamadoHistoricoEntity> findByChamadoOrderByDataAcaoAsc(ChamadoEntity chamado); // Corrigido para ChamadoEnitity
}
