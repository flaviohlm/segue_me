package br.gov.to.santuario.seg.service;

import br.gov.to.santuario.seg.domain.Menu;
import br.gov.to.santuario.seg.repository.IMenuRepository;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flavio.madureira
 */
@Service
public class MenuService {
    
    @Autowired
    private IMenuRepository menuRepository;

    public MenuService() {
    }
    
    public void saveMenu(Menu menu){
        menuRepository.save(menu);
    }
    
    public void saveMenu(List<Menu> menu){
        menuRepository.save(menu);
    }
    
    public void deleteMenu(Menu menu){
        menuRepository.delete(menu);
    }
    
    public List<Menu> findAllMenu(){
        return menuRepository.findAllMenu();
    }
    
    public List<Menu> findByAtivo(){
        return menuRepository.findByAtivo();
    }
    
    public Menu findOneMenu(Integer codigo){
        return menuRepository.findOne(codigo);
    }    

    public List<Menu> findByPerfil(Integer id){
        return menuRepository.findByPerfil(id);
    }
    
    public List<Menu> findByNivel(Integer nivel){
        return menuRepository.findByNivel(nivel);
    }
    
    public List<Menu> findByPai(Integer idPai){
        return menuRepository.findByPai(idPai);
    }
    
    public List<Menu> findByPaiAsc(Integer idPai){
        return menuRepository.findByPaiAsc(idPai);
    }
    
    public List<Menu> findByPaiOrdem(Integer idPai, Integer ordem){
        return menuRepository.findByPaiOrdem(idPai, ordem);
    }      

    public List<Menu> findByMenuNivel(Integer id) {
        return menuRepository.findByMenuNivel(id);
    }
    
    public Menu findByUrl(String url) {
        url = "%"+url+"%";
        List<Menu> lista = menuRepository.findByUrl(url);        
        if(lista.size() > 0){
            return lista.get(0);
        }
        return null;
    }

    
    public TreeNode createCheckboxDocuments(List<Menu> listaMenuPerfil) {
        List<Menu> listaMenus = menuRepository.findAllMenu();
        
        List<Menu> listaMenuPerfilAux = new ArrayList<>();
        if(listaMenuPerfil != null){
           listaMenuPerfilAux = listaMenuPerfil;
        }
                
        TreeNode root = null;
        for(Menu m : listaMenus){
            if(m.getNivel() == 0){//Procura a raiz (O menu geral que e o pai dos outros menus) - nivel 0
                root = new CheckboxTreeNode(m, null);
                root.setExpanded(true);                
                List<Menu> listaFilhos = menuRepository.findByPai(m.getId());
                
                TreeNode filhos;
                for(Menu m1 : listaFilhos){//Para cada menu Filho do menu geral, busca-se os filhos dele - nivel 1
                    filhos = new CheckboxTreeNode(m1, root);
                    filhos.setExpanded(true);
                    //filhos.setSelected(listaMenuPerfilAux.contains(m1));
                    
                    List<Menu> listaNetos = menuRepository.findByPai(m1.getId());
                    TreeNode netos;
                    for(Menu m2 : listaNetos){//Para cada menu Filho do menu geral, busca-se os filhos dele - nivel 2
                        netos = new CheckboxTreeNode(m2, filhos);
                        netos.setExpanded(true);
                        netos.setSelected(listaMenuPerfilAux.contains(m2));

                        //O processamento nesse sistema vai parar no nivel 2. Mas caso o sistema tenha mais niveis, eh so ajustar as suas particularidades
                        List<Menu> listaBisnetos = menuRepository.findByPai(m2.getId());
                        TreeNode bisnetos;
                        for(Menu m3 : listaBisnetos){
                            bisnetos = new CheckboxTreeNode(m3, netos);
                            bisnetos.setExpanded(true);
                            bisnetos.setSelected(listaMenuPerfilAux.contains(m3));
                        }
                    }                    
                }                                
            }
        }         
        return root;
    }    

}
