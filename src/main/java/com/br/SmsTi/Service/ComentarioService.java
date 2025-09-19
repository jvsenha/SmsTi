package com.br.SmsTi.Service;

import com.br.SmsTi.DTO.ComentarioRequest;
import com.br.SmsTi.DTO.ComentarioResponse;
import com.br.SmsTi.Entity.ChamadoEnitity;
import com.br.SmsTi.Entity.ComentarioEntity;
import com.br.SmsTi.Entity.UserEntity;
import com.br.SmsTi.Repository.ChamadoRepository;
import com.br.SmsTi.Repository.ComentarioRepository;
import com.br.SmsTi.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ComentarioResponse criarComentario(Long chamadoId, String usuarioEmail, ComentarioRequest request) {
        ChamadoEnitity chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        UserEntity usuario = userRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ComentarioEntity novoComentario = new ComentarioEntity();
        novoComentario.setDescricao(request.getDescricao());
        novoComentario.setDataComentario(LocalDate.now());
        novoComentario.setChamado(chamado);
        novoComentario.setUsuario(usuario);

        ComentarioEntity salvo = comentarioRepository.save(novoComentario);

        return new ComentarioResponse(salvo.getId(), salvo.getDescricao(), salvo.getUsuario().getNome(), salvo.getDataComentario());
    }

    public List<ComentarioResponse> getComentariosPorChamado(Long chamadoId) {
        ChamadoEnitity chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        return chamado.getComentarios().stream()
                .map(comentario -> new ComentarioResponse(comentario.getId(), comentario.getDescricao(), comentario.getUsuario().getNome(), comentario.getDataComentario()))
                .collect(Collectors.toList());
    }
}
