package br.gov.to.santuario.seg.view;

import br.gov.to.santuario.seg.util.FacesMessages;
import br.gov.to.santuario.seg.domain.Menu;
import br.gov.to.santuario.seg.domain.Perfil;
import br.gov.to.santuario.seg.service.MenuService;
import br.gov.to.santuario.seg.service.PerfilService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class MenuBean implements Serializable {
    
    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;
    
    @ManagedProperty(value = "#{perfilService}")
    private PerfilService perfilService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer idElemento;    
    private List<Menu> listaMenus;    
    private List<Perfil> listaPerfil;
    private List<Perfil> listaPerfilSelecionados;
    private Menu menu = new Menu();
    
    //SALVAR
    public String salvar() {
               
        List<Menu> listaMenusAux = menuService.findByPai(menu.getPai().getId());        
        Integer ordemAux;
        if(menu.getOrdem() == null || menu.getOrdem() == 0){  //Se não existir nenhuma ordem definida, seta o menu para a ultima posicao dos filhos
            if(listaMenusAux.size() > 0){
            ordemAux = listaMenusAux.get(0).getOrdem()+1;
            }else{
                ordemAux = 1;
            }            
            menu.setOrdem(ordemAux);
        } else if(menuService.findByPaiOrdem(menu.getPai().getId(), menu.getOrdem()).size() > 0){//Se houver outro menu definido na mesma ordem            
            for(Menu aux : listaMenusAux){//Para cada item, verifica se a ordem dele, é maior q a indicada no formulario, se for incrementa +1 e salva no banco de dados
                ordemAux = aux.getOrdem();
                if(ordemAux >= menu.getOrdem()){
                    aux.setOrdem(ordemAux+1);
                    menuService.saveMenu(aux);
                }
            }            
        }
        
        if(!menu.getUrl().contains("?faces-redirect=true")){
            menu.setUrl(menu.getUrl()+"?faces-redirect=true");
        }
        
        if(menu.getIcone()==null || menu.getIcone().equals("")){//Isso aqui evita inserir no banco de dados "", pq qnd o sistema gera o menu as "" se transformam em um icone    
            menu.setIcone(null);
        }
        menu.setAtivo(true);//Esse atributo foi retirado do formulario, mas deixado aqui para atualizacoes futuras.
        menu.setPerfis(listaPerfilSelecionados);                
        if(idElemento == null){            
            menuService.saveMenu(menu);
            menu = new Menu();
            messages.info("Menu adicionado com sucesso.");
        }else{            
            menuService.saveMenu(menu);
            menu = new Menu();
            messages.info("Menu atualizado com sucesso.");
        }
        
        return "/configuracoes/menu/index?faces-redirect=true";
    }
    
    //CARREGA MODEL
    public void loadModel() {
        if (idElemento != null) {
            menu = menuService.findOneMenu(idElemento); 
            listaPerfilSelecionados = menu.getPerfis();
        }
    }        
    
    //DELETAR
    public void delete() {
        try {            
            //Reordena os menus que sao 'irmaos' do menu a ser excluido
            List<Menu> listaMenusAux = menuService.findByPai(menu.getPai().getId());
            if(!listaMenusAux.isEmpty()){
                listaMenusAux.remove(menu);
                Integer orderAux = listaMenusAux.size();
                for(int i = 0; i<listaMenusAux.size(); i++){
                    listaMenusAux.get(i).setOrdem(orderAux);
                    orderAux--;
                }
                menuService.saveMenu(listaMenusAux); 
            }   
            //Exclui o menu do banco
            menuService.deleteMenu(menu); 
            refresh();
            messages.info("Menu excluído com sucesso.");
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, "LOG: ", ex);
            messages.error("Não foi possível excluir este Menu.");
        }
    }
    
    //ATUALIZA DATATABLE AO EXCLUIR
    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse(); //Optional
    }
    
    //REDIRECIONAR PARA EDIT
    public String gotoMenuEdit(){
        menu = new Menu();
        return "/configuracoes/menu/edit?faces-redirect=true";
    }
    
    
    //FORMA MENU BLOCOS
    public List<Menu> menuModulo(List<Menu> modulo, Integer nivel, Integer menuPaiId) {
        List<Menu> retorno = new ArrayList<>();
        if(modulo!=null){
            for (Menu menuM : modulo) {
                if (Objects.equals(menuM.getNivel(), nivel) && Objects.equals(menuPaiId, menuM.getPai().getId())) {
                    retorno.add(menuM);
                }
            }
        }
        return retorno;
    }    
    
    
    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public PerfilService getPerfilService() {
        return perfilService;
    }

    public void setPerfilService(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public List<Menu> getListaMenus() {
        
        if(menu != null && menu.getNivel() != null && menu.getNivel() > 0){
            listaMenus = menuService.findByNivel(menu.getNivel() - 1);
            return listaMenus;
        }
        
        if(listaMenus == null){
            listaMenus = menuService.findAllMenu();
        }
        
        return listaMenus;
    }

    public void setListaMenus(List<Menu> listaMenus) {
        this.listaMenus = listaMenus;
    }

    public List<Perfil> getListaPerfil() {
        return listaPerfil = perfilService.findAllPerfil();
    }

    public void setListaPerfil(List<Perfil> listaPerfil) {
        this.listaPerfil = listaPerfil;
    }

    public List<Perfil> getListaPerfilSelecionados() {
        return listaPerfilSelecionados;
    }

    public void setListaPerfilSelecionados(List<Perfil> listaPerfilSelecionados) {
        this.listaPerfilSelecionados = listaPerfilSelecionados;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
            
}
