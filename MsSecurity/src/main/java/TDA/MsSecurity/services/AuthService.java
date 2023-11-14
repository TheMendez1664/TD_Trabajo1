package TDA.MsSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TDA.MsSecurity.model.modelUsuario;
import TDA.MsSecurity.repository.IAuthRepository;

@Service
public class AuthService {

    @Autowired
    IAuthRepository authRepository;

    // Obtener todos los usuarios
    public List<modelUsuario> getAllUsers() {
        return (List<modelUsuario>) authRepository.findAll();
    }

    // Actualizar usuario por ID
    public modelUsuario updateUser(modelUsuario user) {
        return authRepository.save(user);
    }

    // Encontrar usuario por ID
    public modelUsuario getUserById(int id) {
        return authRepository.findById(id).orElse(null);
    }

    // Borrar usuario por ID
    public boolean deleteUser(int id) {
        Optional<modelUsuario> user = authRepository.findById(id);
        if (user.isPresent()) {
            authRepository.delete(user.get());
            return true;
        }
        return false;
    }

    // Crear un nuevo usuario
    public modelUsuario createUser(modelUsuario newUser) {
        return authRepository.save(newUser);
    }

}