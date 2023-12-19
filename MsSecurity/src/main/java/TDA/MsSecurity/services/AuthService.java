package TDA.MsSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TDA.MsSecurity.model.modelUsuario;
import TDA.MsSecurity.repository.IAuthRepository;

@Service
public class AuthService implements IAuthService{

    @Autowired
    IAuthRepository authRepository;

    @Override
    public modelUsuario add(modelUsuario user) {
        return authRepository.save(user);
    }

    @Override
    public modelUsuario update(modelUsuario user) {
        return authRepository.save(user);
    }

    @Override
    public boolean delete(int id) {        
        authRepository.deleteById(id);
        return true;
    }

    @Override
    public List<modelUsuario> findAll() {
        return (List<modelUsuario>) authRepository.findAll();
    }

    @Override
    public modelUsuario findById(int id) {
        Optional<modelUsuario> model = authRepository.findById(id);
        return model.get();
    }

}