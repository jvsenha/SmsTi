package com.br.SmsTi.Service;

import com.br.SmsTi.DTO.LoginRequest;
import com.br.SmsTi.DTO.LoginResponse;
import com.br.SmsTi.DTO.UserDTO;
import com.br.SmsTi.Entity.UserEntity;
import com.br.SmsTi.Enum.Role;
import com.br.SmsTi.Enum.Status;
import com.br.SmsTi.Mapper.UserMapper;
import com.br.SmsTi.Repository.UserRepository;
import com.br.SmsTi.Security.SecurityService;
import com.br.SmsTi.Util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private SecurityService securityService;

    @Autowired
    private UserMapper userMapper;

    public LoginResponse autenticar(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(loginRequest.getSenha(), user.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        }

        if (user.getStatus() != Status.STATUS_ATIVO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário inativo");
        }

        String token = jwtUtil.generateToken(user);

        return new LoginResponse(
                token,
                user.getEmail(),
                user.getNome(),
                user.getRole().name(),
                user.getId()
        );
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public UserDTO cadastrar(UserDTO dto) {
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        userMapper.updateEntityFromDto(dto, user);
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            user.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public void alterarSenhaAdmFunc(Long id, String novaSenha) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        user.setSenha(passwordEncoder.encode(novaSenha));
        userRepository.save(user);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication)")
    public void deletarUsuario(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        userRepository.delete(user);
    }

    public UserDTO buscarUsuario(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return userMapper.toUserDTO(user);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public List<UserDTO> listarTodos() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public List<UserDTO> listarPorRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public List<UserDTO> listarAtivos() {
        return userRepository.findByStatus(Status.STATUS_ATIVO).stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public List<UserDTO> listarInativos() {
        return userRepository.findByStatus(Status.STATUS_INATIVO).stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public Status alternarStatus(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (user.getStatus() == Status.STATUS_ATIVO) {
            user.setStatus(Status.STATUS_INATIVO);
        } else {
            user.setStatus(Status.STATUS_ATIVO);
        }

        userRepository.save(user);
        return user.getStatus();
    }
}