package com.br.SmsTi.Repository;

import com.br.SmsTi.Entity.ChamadoEntity;
import com.br.SmsTi.Enum.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<ChamadoEntity, Long> {

    public List<ChamadoEntity> findByStatusChamado(StatusChamado status);
}
