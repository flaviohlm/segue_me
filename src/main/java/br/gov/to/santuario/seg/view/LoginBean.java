package br.gov.to.santuario.seg.view;

import br.gov.to.santuario.seg.util.ListaAnos;
import br.gov.to.santuario.seg.util.PegarDateHora;
import br.gov.to.santuario.seg.domain.Menu;
import br.gov.to.santuario.seg.domain.Perfil;
import br.gov.to.santuario.seg.domain.PerfilUsuario;
import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.service.AuthenticationService;
import br.gov.to.santuario.seg.service.MenuService;
import br.gov.to.santuario.seg.service.PerfilUsuarioService;
import br.gov.to.santuario.seg.service.ParticipanteService;
import br.gov.to.santuario.seg.util.UsuarioUtil;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
 
    
    @ManagedProperty(value = "#{authenticationService}")
    private AuthenticationService authenticationService;
    
    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;     
    
    @ManagedProperty(value = "#{perfilUsuarioService}")
    private PerfilUsuarioService perfilUsuarioService;
    
    @ManagedProperty(value = "#{participanteService}")
    private ParticipanteService participanteService;    
        
    //private UserDetailsServiceImpl userDetailsServiceImpl; 
    private Participante participante = new Participante();
    private MenuModel modelMenu = new DefaultMenuModel();    
    private Perfil perfil;
    private Boolean autenticado = false;        
    private List<String> paginasPermitidas = new ArrayList<>();
    private List<PerfilUsuario> listaPerfilUsuario = new ArrayList<>();
    private List< Menu> menusAcesso;
    
    private String cpf = null;
    private Participante usuarioTrocaSenha;
    private String password;
    private String confirmaPassword;
    private Integer anoAtual;
    PegarDateHora pegarDataHora = new PegarDateHora();
    

    // LOGIN
    public String login() throws NoSuchAlgorithmException {        

        listaPerfilUsuario = perfilUsuarioService.findATivosByCpf(participante.getCpf().replaceAll("[^0-9]", ""));
        
        System.out.println(listaPerfilUsuario.get(0).getParticipante());
        if (listaPerfilUsuario != null && listaPerfilUsuario.size() > 0) {
            
            if (listaPerfilUsuario.get(0).getParticipante().getRedefinirSenha()) {
                usuarioTrocaSenha = listaPerfilUsuario.get(0).getParticipante();
                return "/configuracoes/redefinirSenhaUsuario/index.xhtml?faces-redirect=true";
            }

            if(listaPerfilUsuario.size() > 1){
                 return "/login/selecionarPerfil";
            }
            
            //GERA O MENU DE ACORDO COM O USUARIO LOGADO            
            setMenusAcesso(menuService.findByMenuNivel(listaPerfilUsuario.get(0).getPerfil().getId()));

            boolean success = authenticationService.login(participante.getCpf().replaceAll("[^0-9]", ""), participante.getPassword());
            if (!success) {                
                return "/login/index";
            }
            
            //Atualiza o ultimo perfil/data e hora do ultimo login
            participante = listaPerfilUsuario.get(0).getParticipante();  
//            participante.setUltimoLoginPerfil(listaPerfilUsuario.get(0).getPerfil());
//            participante.setDataUltimoLogin(pegarDataHora.getDateTime());
//            participante.setHoraUltimoLogin(pegarDataHora.getTime());
            participanteService.saveParticipante(participante);
            
            
            return "/index?faces-redirect=true";
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário inválido ou sem perfil vinculado.", ""));
            return "/login/index";
        }
    }
       
    // LOGOUT
    public String logout() {
        authenticationService.logout();
        return "/login/index?faces-redirect=true";
    }

    // USUÁRIO LOGADO
    public Participante getUsuarioLogado() {
        return authenticationService.getUsuarioLogado();
    }
    
    //SELECIONA O PERFIL CASO O USUARIO TENHA DOIS OU MAIS
    public String escolhePerfil(){
        boolean sucesso = authenticationService.login(participante.getCpf().replaceAll("[^0-9]", ""), participante.getPassword());
        
        //GERA O MENU DE ACORDO COM O USUARIO LOGADO                       
        setMenusAcesso(menuService.findByMenuNivel(perfil.getId()));
        
        if (!sucesso) {            
            return "/login/index";
        }
        
        //Atualiza o ultimo perfil/data e hora do ultimo login
        participante = listaPerfilUsuario.get(0).getParticipante();  
//        participante.setUltimoLoginPerfil(perfil);
//        participante.setDataUltimoLogin(pegarDataHora.getDateTime());
//        participante.setHoraUltimoLogin(pegarDataHora.getTime());
        participanteService.saveParticipante(participante);
        
        return "/index?faces-redirect=true";
        
    }
    
    //TROCAR SENHA QUANDO A SENHA FOR IGUAL AO CPF DO USUARIO
    public String trocarSenha() throws NoSuchAlgorithmException{        
        if(usuarioTrocaSenha == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acesso restrito!!",""));
        }else{
            if(!password.equals(confirmaPassword)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A senhas não conferem!",""));
            } else if(password.length() < 6){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha muito curta. Digite uma senha maior que 6 caracteres.",""));
                return "/configuracoes/redefinirSenhaUsuario/index";
            } else{
                usuarioTrocaSenha.setPassword(password);
                usuarioTrocaSenha.setRedefinirSenha(false);
                participanteService.updateParticipante(usuarioTrocaSenha);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso!",""));
            }
        }
        usuarioTrocaSenha = null;
        return "/login/index";
    }   
    
    //REDIRECIONA PARA O INDEX2 CASO O USUARIO NAO TENHA AINDA NENHUMA PAGINA FAVORITA
    public void gotoIndex() {
//        if (getListaFavoritosMenuUsuario().isEmpty()) {
//            FacesContext context = FacesContext.getCurrentInstance();
//            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
//            navigationHandler.handleNavigation(context, null, "/index2?faces-redirect=true");
//        }
    }    
    
    //VERIFICA SE O USUARIO ESTA LOGADO, CASO SIM, REDIRECIONA-O PARA A PAGINA INICIAL
    public void checkAuthentication() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        if (externalContext.getUserPrincipal() != null) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml?faces-redirect=true");
        }
    }
    
    //GETTERS AND SETTERS    
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }   

    public MenuModel getModelMenu() {          
        return modelMenu;
    }

    public void setModelMenu(MenuModel modelMenu) {
        this.modelMenu = modelMenu;
    }

    public List<String> getPaginasPermitidas() {
        return paginasPermitidas;
    }

    public void setPaginasPermitidas(List<Menu> menus) {
        paginasPermitidas = new ArrayList<>();
        for (Menu m : menus) {
            paginasPermitidas.add(m.getUrl());
        }
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public PerfilUsuarioService getPerfilUsuarioService() {
        return perfilUsuarioService;
    }

    public void setPerfilUsuarioService(PerfilUsuarioService perfilUsuarioService) {
        this.perfilUsuarioService = perfilUsuarioService;
    }

    public List<PerfilUsuario> getListaPerfilUsuario() {
        return listaPerfilUsuario;
    }

    public void setListaPerfilUsuario(List<PerfilUsuario> listaPerfilUsuario) {
        this.listaPerfilUsuario = listaPerfilUsuario;
    }

    public Boolean getAutenticado() {
        return autenticado;
    }

    public void setAutenticado(Boolean autenticado) {
        this.autenticado = autenticado;
    }

    public List<Menu> getMenusAcesso() {
        return menusAcesso;
    }

    public void setMenusAcesso(List<Menu> menusAcesso) {
        this.menusAcesso = menusAcesso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmaPassword() {
        return confirmaPassword;
    }

    public void setConfirmaPassword(String confirmaPassword) {
        this.confirmaPassword = confirmaPassword;
    }

    public ParticipanteService getParticipanteService() {
        return participanteService;
    }

    public void setParticipanteService(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Participante getUsuarioTrocaSenha() {
        if(UsuarioUtil.getUsuario() != null){
            usuarioTrocaSenha=UsuarioUtil.getUsuario();
        }
        return usuarioTrocaSenha;
    }

    public void setUsuarioTrocaSenha(Participante usuarioTrocaSenha) {
        this.usuarioTrocaSenha = usuarioTrocaSenha;
    }

    public Integer getAnoAtual() {
        return anoAtual = ListaAnos.getAnoAtualReal();
    }

    public void setAnoAtual(Integer anoAtual) {
        this.anoAtual = anoAtual;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public PegarDateHora getPegarDataHora() {
        return pegarDataHora;
    }

    public void setPegarDataHora(PegarDateHora pegarDataHora) {
        this.pegarDataHora = pegarDataHora;
    }

}