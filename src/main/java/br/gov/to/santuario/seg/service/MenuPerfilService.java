package br.gov.to.santuario.seg.service;

import br.gov.to.santuario.seg.domain.MenuUsuario;
import br.gov.to.santuario.seg.repository.IMenuPerfilRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flavio.madureira
 */
@Service
public class MenuPerfilService {
    
    @Autowired
    private IMenuPerfilRepository menuUsuarioRepository;

    public MenuPerfilService() {
    }
    
    public void saveMenuUsuario(MenuUsuario menuUsuario){
        menuUsuarioRepository.save(menuUsuario);
    }
    
    public void deleteMenuUsuario(MenuUsuario menuUsuario){
        menuUsuarioRepository.delete(menuUsuario);
    }
    
    public List<MenuUsuario> findAllMenuUsuario(){
        return menuUsuarioRepository.findAll();
    }
    
    public MenuUsuario findOneMenuUsuario(Integer codigo){
        return menuUsuarioRepository.findOne(codigo);
    }
    
}
