package br.gov.to.santuario.seg.view;

import br.gov.to.santuario.seg.util.FacesMessages;
import br.gov.to.santuario.seg.domain.Menu;
import br.gov.to.santuario.seg.domain.Perfil;
import br.gov.to.santuario.seg.service.MenuService;
import br.gov.to.santuario.seg.service.PerfilService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.primefaces.model.TreeNode;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ana.dias
 */
@ManagedBean
@ViewScoped
public class PerfilBean implements Serializable {

    @ManagedProperty(value = "#{perfilService}")
    private PerfilService perfilService;

    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;

    private FacesMessages messages = new FacesMessages();    
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PerfilBean.class);
    private Perfil perfil = new Perfil();    
    private List<Perfil> listaPerfil;// = new ArrayList<>();
    private Integer idElemento = null;
    
    private TreeNode listaMenu;
    private TreeNode[] listaMenuSelecionados;            

    public PerfilBean() {        
    }

    @PostConstruct
    public void init() {  
        if(perfil != null){
            listaMenu = menuService.createCheckboxDocuments(perfil.getMenus());
        }else{
            listaMenu = menuService.createCheckboxDocuments(null);
        }
    }

    //SALVAR
    public String salvar() {        
        if(perfil.getNivel() == null ){//&& componenteUtil.getNivelPerfil() > perfil.getNivel()
            messages.error("Acesso negado.");
            messages.info("Você não tem permissão para alterar as configurações deste perfil.");            
        }
        else{
            //Trata os menu selecionado na tree para que sejam objetos do tipo menu
            List<Menu> listaMenuSelecionadosAux = new ArrayList<>();
            Menu menuAux;
            if(listaMenuSelecionados != null && listaMenuSelecionados.length > 0){            
                for(TreeNode node : listaMenuSelecionados) {                
                    menuAux = (Menu) node.getData();                
                    if(!listaMenuSelecionadosAux.contains(menuAux.getPai())){                
                        listaMenuSelecionadosAux.add(menuAux.getPai());
                    }

                    if(!listaMenuSelecionadosAux.contains(menuAux)){                
                        listaMenuSelecionadosAux.add(menuAux);
                    }                             
                }
            }

            if(idElemento == null){
                perfil.setMenus(listaMenuSelecionadosAux);            
                perfilService.savePerfil(perfil);
                perfil = new Perfil();
                messages.info("Perfil adicionado com sucesso.");
            }else{
                perfil.setMenus(listaMenuSelecionadosAux);
                perfilService.savePerfil(perfil);
                perfil = new Perfil();
                messages.info("Perfil atualizado com sucesso.");
            }
        }
        
        return "/configuracoes/perfil/index?faces-redirect=true";
    }

    //DELETAR
    public void delete() {
        if(perfil.getNivel() == null){//&& componenteUtil.getNivelPerfil() > perfil.getNivel()
            messages.error("Acesso negado.");
            messages.info("Você não tem permissão para alterar as configurações deste perfil.");
            return;
        }
        
        try {
            perfilService.deletePerfil(perfil);
            refresh();
            messages.info("Perfil excluído com sucesso.");
        } catch (Exception ex) {
            Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, "LOG: ", ex);
            messages.error("Não foi possível excluir este Perfil.");
        }
    }

    //IR PARA CADASTRO
    public String gotoGrupoEdit() {
        perfil = new Perfil();
        return "/configuracoes/perfil/cadastrar/index?faces-redirect=true";
    }

    //CARREGA MODEL
    public void loadModel() {        
        if (idElemento != null) {
            perfil = perfilService.findOnePerfil(idElemento);            
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

    //GET E SET
    public PerfilService getPerfilService() {
        return perfilService;
    }

    public void setPerfilService(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }    

    public List<Perfil> getListaPerfil() {
        return listaPerfil = perfilService.findAllPerfil();
    }

    public void setListaPerfil(List<Perfil> listaPerfil) {
        this.listaPerfil = listaPerfil;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public TreeNode getListaMenu() {
        return listaMenu;
    }

    public void setListaMenu(TreeNode listaMenu) {
        this.listaMenu = listaMenu;
    }

    public TreeNode[] getListaMenuSelecionados() {
        return listaMenuSelecionados;
    }

    public void setListaMenuSelecionados(TreeNode[] listaMenuSelecionados) {
        this.listaMenuSelecionados = listaMenuSelecionados;
    }
    
}
