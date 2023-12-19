package TDA.MsSecurity.model;

import org.springframework.stereotype.Component;

import TDA.MsSecurity.dto.AuthRequest;

@Component
public class UsuarioMapper {
 
    public AuthRequest entityToDto(modelUsuario usuario) {
        AuthRequest dto = new AuthRequest();
        dto.setUsuario(usuario.getUsuario());
        dto.setClave(usuario.getClave());
        return dto;
    }

    public modelUsuario dtoToEntity(AuthRequest dto) {
        modelUsuario usuario = new modelUsuario();
        usuario.setUsuario(dto.getUsuario());
        usuario.setClave(dto.getClave());
        return usuario;
    }

}
