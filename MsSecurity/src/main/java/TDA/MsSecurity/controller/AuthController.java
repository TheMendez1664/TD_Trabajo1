package TDA.MsSecurity.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TDA.MsSecurity.dto.AuthRequest;
import TDA.MsSecurity.model.UsuarioMapper;
import TDA.MsSecurity.model.modelUsuario;
import TDA.MsSecurity.services.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @Autowired
    UsuarioMapper usuarioMapper;

    //Busqueda general
    @GetMapping("/getAll")
    public List<AuthRequest> getAll() {
        List<modelUsuario> usuarios = authService.findAll();
        return usuarios.stream()
                       .map(usuarioMapper::entityToDto)
                       .collect(Collectors.toList());
    } 
    
    //Busqueda por ID
    @GetMapping("/getById")
    public AuthRequest getById(int id) {
        modelUsuario usuario = authService.findById(id);
        return usuarioMapper.entityToDto(usuario);
    }

    //Crear Usuario
    @PostMapping("/create")
    public AuthRequest create(@RequestBody AuthRequest authRequest) {
        modelUsuario usuario = usuarioMapper.dtoToEntity(authRequest);
        usuario = authService.add(usuario);
        return usuarioMapper.entityToDto(usuario);
    }

    //Modificar Usuario
    @PutMapping("/update")
    public AuthRequest update(@RequestBody AuthRequest authRequest) {
        modelUsuario usuario = usuarioMapper.dtoToEntity(authRequest);
        usuario = authService.update(usuario);
        return usuarioMapper.entityToDto(usuario);
    }

    //Eliminar Usuario
    @DeleteMapping("/delete")
    public boolean delete(int id) {
        return authService.delete(id);
    }
}
