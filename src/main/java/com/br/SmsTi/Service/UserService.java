package com.br.SmsTi.Service;

import com.br.SmsTi.DTO.LoginRequest;
import com.br.SmsTi.DTO.LoginResponse;
import com.br.SmsTi.DTO.UserDTO;
import com.br.SmsTi.Entity.UserEntity;
import com.br.SmsTi.Enum.Status;
import com.br.SmsTi.Exception.AuthenticationException;
import com.br.SmsTi.Exception.AuthorizationException;
import com.br.SmsTi.Exception.ResourceNotFoundException;
import com.br.SmsTi.Mapper.UserMapper;
import com.br.SmsTi.Repository.UserRepository;
import com.br.SmsTi.Util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserMapper userMapper;

    public LoginResponse autenticar(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(loginRequest.getSenha(), user.getSenha())) {
            throw new AuthenticationException("Usuário ou senha inválidos");
        }

        if (user.getStatus() != Status.STATUS_ATIVO) {
            throw new AuthorizationException("Usuário inativo. Acesso negado.");
        }

        String token = jwtUtil.generateToken(user);
        return new LoginResponse(token, user.getEmail(), user.getNome(), user.getRole().name(), user.getId());
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public UserDTO cadastrar(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado."); // Outra exceção que podemos tratar no handler
        }
        UserEntity user = userMapper.toEntity(dto);
        user.setSenha(passwordEncoder.encode(dto.getSenha()));
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.STATUS_ATIVO);
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public UserDTO atualizar(Long id, UserDTO dto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado."));
        userMapper.updateEntityFromDto(dto, user);
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            user.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication)")
    public void deletarUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado para deleção.");
        }
        userRepository.deleteById(id);
    }

    public UserDTO buscarUsuario(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado."));
        return userMapper.toUserDTO(user);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public List<UserDTO> listarTodos() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public UserDTO alternarStatus(Long id) { // Retornando o DTO completo
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado."));
        user.setStatus(
                user.getStatus() == Status.STATUS_ATIVO ? Status.STATUS_INATIVO : Status.STATUS_ATIVO
        );
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

}