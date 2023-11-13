package TDA.MsSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
import TDA.MsSecurity.model.modelUsuario;
 
@Repository
public interface IAuthRepository extends CrudRepository<modelUsuario, Integer> {
   
}