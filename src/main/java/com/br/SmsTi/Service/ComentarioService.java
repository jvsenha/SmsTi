package com.br.SmsTi.Service;

import com.br.SmsTi.DTO.ComentarioRequest;
import com.br.SmsTi.DTO.ComentarioResponse;
import com.br.SmsTi.Entity.ChamadoEntity;
import com.br.SmsTi.Entity.ComentarioEntity;
import com.br.SmsTi.Entity.UserEntity;
import com.br.SmsTi.Repository.ChamadoRepository;
import com.br.SmsTi.Repository.ComentarioRepository;
import com.br.SmsTi.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2', 'Nivel1')")
    public ComentarioResponse criarComentario(Long chamadoId, String usuarioEmail, ComentarioRequest request) {
        ChamadoEntity chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado"));

        UserEntity usuario = userRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        ComentarioEntity novoComentario = new ComentarioEntity();
        novoComentario.setDescricao(request.getDescricao());
        novoComentario.setDataComentario(LocalDateTime.now()); // Usando LocalDateTime
        novoComentario.setChamado(chamado);
        novoComentario.setUsuario(usuario);

        ComentarioEntity salvo = comentarioRepository.save(novoComentario);

        return new ComentarioResponse(salvo.getId(), salvo.getDescricao(), salvo.getUsuario().getNome(), salvo.getDataComentario());
    }

    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2', 'Nivel1')")
    public List<ComentarioResponse> getComentariosPorChamado(Long chamadoId) {
        ChamadoEntity chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado"));

        return chamado.getComentarios().stream()
                .map(comentario -> new ComentarioResponse(comentario.getId(), comentario.getDescricao(), comentario.getUsuario().getNome(), comentario.getDataComentario()))
                .collect(Collectors.toList());
    }
}