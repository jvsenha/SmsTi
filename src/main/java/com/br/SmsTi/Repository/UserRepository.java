package com.br.SmsTi.Repository;

import com.br.SmsTi.Entity.UserEntity;
import com.br.SmsTi.Enum.Role;
import com.br.SmsTi.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserEntity> findByRole(Role role);
    List<UserEntity> findByStatus(Status status);
}
