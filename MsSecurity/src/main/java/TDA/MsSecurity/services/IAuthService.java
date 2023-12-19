package TDA.MsSecurity.services;

import java.util.List;

import TDA.MsSecurity.model.modelUsuario;

public interface IAuthService {
    
    public modelUsuario add (modelUsuario user);
    public modelUsuario update (modelUsuario user);
    public boolean delete (int id);
    public List<modelUsuario> findAll ();
    public modelUsuario findById (int id);
}
