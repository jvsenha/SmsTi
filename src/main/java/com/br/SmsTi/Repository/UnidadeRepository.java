package com.br.SmsTi.Repository;

import com.br.SmsTi.Entity.UnidadeEntity;
import com.br.SmsTi.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<UnidadeEntity, Long> {
    List<UnidadeEntity> findByStatus(Status status);
}
