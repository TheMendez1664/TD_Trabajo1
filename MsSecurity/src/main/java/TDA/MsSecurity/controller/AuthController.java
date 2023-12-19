package TDA.MsSecurity.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TDA.MsSecurity.ControllerEx.ApiResponse;
import TDA.MsSecurity.dto.AuthRequest;
import TDA.MsSecurity.model.UsuarioMapper;
import TDA.MsSecurity.model.UsuarioNotFoundException;
import TDA.MsSecurity.model.modelUsuario;
import TDA.MsSecurity.services.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    IAuthService authService;

    @Autowired
    UsuarioMapper usuarioMapper;

    //Busqueda general
     @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<AuthRequest>>> getAll() {
        try{
            List<modelUsuario> usuario = authService.findAll();
            List<AuthRequest> authRequests =usuario.stream()
            .map(usuarioMapper::entityToDto)
            .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
             "Consulta de listado extitosa",authRequests));
        } catch(Exception ex){
            logger.error("Error en el metodo getAll", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Error al procesar la solicitud", null));
        }
        
    } 
    
    //Busqueda por ID
     @GetMapping("/getById")
    public ResponseEntity<ApiResponse<AuthRequest>> getById(@RequestParam int id) {
        try{
            modelUsuario usuarioModel = authService.findById(id);
            if (usuarioModel !=null) {
                AuthRequest authRequest = usuarioMapper.entityToDto(usuarioModel);
                return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Consulta de Busqueda por ID extitosa", authRequest));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Usuario no creado",null ));
            }
        }catch(Exception ex){
            logger.error("Error en el metodo getById", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Error al Buscar usuario" , null));
        }
    }

    //Crear Usuario
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AuthRequest>> create(@RequestBody AuthRequest authRequest) {
        try{
            modelUsuario usuarioModel = usuarioMapper.dtoToEntity(authRequest);
            usuarioModel = authService.add(usuarioModel);
            AuthRequest creaAuthRequest = usuarioMapper.entityToDto(usuarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Usuario Creado", creaAuthRequest));
        }catch(Exception ex){
            logger.error("Error en el metodo CREATE", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al Crear", null));
        }
    }

    //Modificar Usuario
     @PutMapping("/update")
    public ResponseEntity<ApiResponse<AuthRequest>> update(@RequestBody AuthRequest authRequest) {
        try{
            // Llamada al servicio para actualizar el usuario
            modelUsuario usuario = authService.update(usuarioMapper.dtoToEntity(authRequest));
            // Construir la respuesta exitosa
            AuthRequest actualizarAuthRequest = usuarioMapper.entityToDto(usuario);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Usuario actualizado", actualizarAuthRequest));
        }catch(UsuarioNotFoundException ex){
            logger.error("Error al actualizar usuario", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null));
        }
        catch(Exception ex){
            logger.error("Error en el metodo UPDATE", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error al actulizar usuario", null));
        }
    }

    //Eliminar Usuario
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        try{
            // Llamada al servicio para eliminar el usuario
            boolean elimminar = authService.delete(id);

            if (elimminar) {
                return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Usuario Eliminado correctamente", null));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), 
                "No se Encontro al Usuario a eliminar", null));
            }
        }catch(Exception ex){
            // Registrar el error utilizando SLF4J
            logger.error("Error en el metodo DELETE", ex);

            // Manejar otras excepciones y devolver una respuesta 500 Internal Server Error con un mensaje genérico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Error al aliminar usuario", null));
        }
}
}