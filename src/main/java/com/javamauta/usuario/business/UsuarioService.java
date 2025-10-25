package com.javamauta.usuario.business;

import com.javamauta.usuario.UsuarioApplication;
import com.javamauta.usuario.business.converter.UsuarioConverter;
import com.javamauta.usuario.business.dto.UsuarioDTO;
import com.javamauta.usuario.infrastructure.entity.Usuario;
import com.javamauta.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO usarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
