package TDA.MsSecurity.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TDA.MsSecurity.dto.AuthRequest;
import TDA.MsSecurity.model.modelUsuario;
import TDA.MsSecurity.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    // Obtener todos los usuarios
    @GetMapping
    public List<modelUsuario> get() {
        return authService.getAllUsers();
    }

    // Actualizar usuario por ID
    @PutMapping("/update/{id}")
    public modelUsuario updateUser(@PathVariable int id, @RequestBody AuthRequest authRequest) {
        modelUsuario existingUser = authService.getUserById(id);
        if (existingUser != null) {
            existingUser.setUsuario(authRequest.getUsuario());
            existingUser.setClave(authRequest.getClave());
            return authService.updateUser(existingUser);
        }
        return null; // Regresa vacio si no hay usuario
    }

    // Encontrar usuario por ID
    @GetMapping("/{id}")
    public modelUsuario getUserById(@PathVariable int id) {
        return authService.getUserById(id);
    }

    // Borrar usuario por ID
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        if (authService.deleteUser(id)) {
            return "Usuario eliminado correctamente";
        } else {
            return "No se pudo eliminar el usuario";
        }
    }

    // Crear un nuevo usuario
    @PostMapping("/create")
    public modelUsuario createUser(@RequestBody AuthRequest authRequest) {
        modelUsuario newUser = new modelUsuario();
        newUser.setUsuario(authRequest.getUsuario());
        newUser.setClave(authRequest.getClave());
        return authService.createUser(newUser);
    }
}
