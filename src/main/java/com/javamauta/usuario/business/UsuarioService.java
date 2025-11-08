package com.javamauta.usuario.business;

import com.javamauta.usuario.business.converter.UsuarioConverter;
import com.javamauta.usuario.business.dto.UsuarioDTO;
import com.javamauta.usuario.infrastructure.entity.Usuario;
import com.javamauta.usuario.infrastructure.exceptions.ConflictException;
import com.javamauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javamauta.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificarEmailExistentes(email);
            if (existe){
                throw  new ConflictException("Email já cadastrado " + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificarEmailExistentes(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){

        // Aqui buscando o emal do usuario atraves do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : nul);

        // Busca os dados do usuário no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow() -> new ResourceNotFoundException("Email não cadastrado");

        // Mesclou os dados que recebemos na requisicao DTO como os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        return  usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
