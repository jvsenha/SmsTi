package com.br.SmsTi.Repository;

import com.br.SmsTi.Entity.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {
}
