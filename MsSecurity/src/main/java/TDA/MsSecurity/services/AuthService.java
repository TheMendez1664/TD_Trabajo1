package TDA.MsSecurity.services;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import TDA.MsSecurity.model.modelUsuario;
import TDA.MsSecurity.repository.IAuthRepository;
 
@Service
public class AuthService {
 
    @Autowired
    IAuthRepository authRepository;
   
    // Obtener todos los usuarios
    public List<modelUsuario>  getAllUsers() {       
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


}