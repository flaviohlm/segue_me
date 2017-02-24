package br.gov.to.santuario.seg.view;

import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.seg.util.FacesMessages;
import br.gov.to.santuario.seg.domain.Perfil;
import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.service.PerfilService;
import br.gov.to.santuario.seg.service.ParticipanteService;
import br.gov.to.santuario.seg.service.PerfilUsuarioService;
import br.gov.to.santuario.seg.util.UsuarioUtil;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
    
    @ManagedProperty(value = "#{perfilUsuarioService}")
    private PerfilUsuarioService perfilUsuarioService;    
    
    @Autowired
    @ManagedProperty(value = "#{participanteService}")
    private ParticipanteService participanteService;
    
    @ManagedProperty(value = "#{perfilService}")
    private PerfilService perfilService;
    
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer idElemento;    
    private Participante usuario = new Participante();
    private List<Participante> listaParticipantes;
    private List<Seguidor> listaSeguidores;
    private List<Perfil> listaPerfis;
    private List<Perfil> listaPerfisSelecionados = new ArrayList<>();      
    private Participante participanteSelecionado;
    private String password;
    private String confirmaPassword;
    
    public void init(){        
    }
    
    //SALVAR O PERFIL DO USUARIO
    public String salvar() throws NoSuchAlgorithmException{ 
        
        //Nao é permitido que um usuario de nivel abaixo, cadastre outro usuario de nivel acima.
//        Participante usuarioLogado = participanteService.findOneParticipante(UsuarioUtil.getId());
//        Integer nivelParticipanteLogado = usuarioLogado.getUltimoLoginPerfil().getNivel();
//        for(Perfil p : listaPerfisSelecionados){               
//            if(nivelParticipanteLogado > p.getNivel()){
//                messages.info("Não é possível cadastrar/editar um usuário com perfil de nível de sistema mais alto que o seu!!!");
//                return "";
//            }
//        }
//        
//        if(idElemento != null){
//            for(Perfil p : usuario.getListaPerfis()){                
//                if(nivelParticipanteLogado > p.getNivel()){
//                    messages.info("Não é possível cadastrar/editar um usuário com perfil de nível de sistema mais alto que o seu!!!");
//                    return "";
//                }
//            }
//        }
        
        try{
            if (idElemento == null) {
                usuario.setPassword(usuario.getCpf());                
            }            
            usuario.setListaPerfis(listaPerfisSelecionados);
            if(!listaPerfisSelecionados.isEmpty()){//Para qnd a pessoa fizer o primeiro login, nao retorne null e bugue tudo
//                usuario.setUltimoLoginPerfil(listaPerfisSelecionados.get(0));
            }
            participanteService.saveParticipante(usuario);
            messages.info("Dados salvos com sucesso!!!");
        }catch(Exception e){
            messages.error("ParticipanteBean->salvar: "+e.getMessage());
        }
        
        return "/configuracoes/usuario/index?faces-redirect=true";
    }
    
    //Buscar Componente pelo CPF
    public void buscarParticipante(){        
        String cpf = getUsuario().getCpf().replaceAll("[^0-9]", "");                 
        Participante componenteAux = participanteService.findByCpf(cpf);                

        if (componenteAux != null) {
            usuario = componenteAux;                
        } else {
            usuario = new Participante();
            messages.info("CPF não encontrado.");    
        }
    }
    
    //CARREGA MODEL
    public void loadModel(){
         if (idElemento != null) {
            usuario = participanteService.findOneParticipante(idElemento);
            listaPerfisSelecionados = usuario.getListaPerfis();            
        }
    }
    
    //CARREGA MODEL USUARIO LOGADO
    public void loadModelParticipanteLogado(){
        usuario = UsuarioUtil.getUsuario();
        listaPerfisSelecionados = usuario.getListaPerfis();
    }
    
    //IR PARA CADASTRO
    public String gotoParticipanteCadastrar() {
        usuario = new Participante();
        return "/configuracoes/usuario/cadastrar/index.xhtml?faces-redirect=true";
    }    
    
    //RESETAR A SENHA DO USUARIO
    public void resetaPassword() throws NoSuchAlgorithmException{        
        usuario.setPassword(usuario.getCpf());
        usuario.setRedefinirSenha(true);
        participanteService.updateParticipante(usuario);
        messages.info("Senha atualizada. A senha do componente é o CPF.");
    }
    
    //DESATIVA O ACESSO DO USUÁRIO NO SISTEMA
    public void desativarParticipante() {   
        usuario.setAtivo(false);
        participanteService.updateParticipante(usuario);
        messages.info("Usuário desativado com sucesso.");
    }
    
    //ATIVA O ACESSO DO USUÁRIO NO SISTEMA
    public void ativarParticipante() {                
        usuario.setAtivo(true);
        participanteService.updateParticipante(usuario);
        messages.info("Acesso do usuário restaurado com sucesso.");
    }
    
    //SALVAR ALTERAÇÕES FEITAS PELO USUARIO NOS DADOS DELE MESMO
    public String update() throws NoSuchAlgorithmException {        
        if (!password.isEmpty()) {
            usuario.setPassword(password);
        }    
        try {
            participanteService.updateParticipante(usuario);
            messages.info("Dados alterados com sucesso!!!");
        } catch (Exception e) {
            messages.error("Não foi possível salvar as alterações.");            
        }
        return "/configuracoes/meus-dados/index?faces-redirect=true";
    }
    
    
    public String ajustaCPF(String cpf){
       String aux = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6,9)+"-"+cpf.substring(9);
       return aux;
    }
    
    public void selecionarParticipante(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segue-me-1.0/configuracoes/usuario/editar/index.xhtml?id=" + participanteSelecionado.getId());
        //return "/configuracoes/usuario/editar/index.xhtml?id="+participanteSelecionado.getId()+"&faces-redirect=true";
    }
    
    //GETTERS AND SETTERS
    public List<Participante> getListaParticipantes() {
        if(listaParticipantes==null){
            listaParticipantes = participanteService.findAllParticipantesUsuarios();
        }
        return listaParticipantes;
    }

    public void setListaParticipantes(List<Participante> listaParticipantes) {
        this.listaParticipantes = listaParticipantes;
    }

    public List<Perfil> getListaPerfis() {
        if(listaPerfis == null){
            listaPerfis = perfilService.findAllPerfil();
        }
        return listaPerfis;
    }

    public void setListaPerfis(List<Perfil> listaPerfis) {
        this.listaPerfis = listaPerfis;
    }

    public PerfilUsuarioService getPerfilUsuarioService() {
        return perfilUsuarioService;
    }

    public void setPerfilUsuarioService(PerfilUsuarioService perfilUsuarioService) {
        this.perfilUsuarioService = perfilUsuarioService;
    }

    public ParticipanteService getParticipanteService() {
        return participanteService;
    }

    public void setParticipanteService(ParticipanteService participanteService) {
        this.participanteService = participanteService;
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

    public Participante getUsuario() {
        return usuario;
    }

    public void setUsuario(Participante usuario) {
        this.usuario = usuario;
    }

    public List<Perfil> getListaPerfisSelecionados() {
        return listaPerfisSelecionados;
    }

    public void setListaPerfisSelecionados(List<Perfil> listaPerfisSelecionados) {
        this.listaPerfisSelecionados = listaPerfisSelecionados;
    }

    public Participante getParticipanteSelecionado() {
        return participanteSelecionado;
    }

    public void setParticipanteSelecionado(Participante participanteSelecionado) {
        this.participanteSelecionado = participanteSelecionado;
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

    public SeguidorService getSeguidorService() {
        return seguidorService;
    }

    public void setSeguidorService(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    public List<Seguidor> getListaSeguidores() {
        if(listaSeguidores == null){
            listaSeguidores = seguidorService.findAllSeguidor();
        }
        return listaSeguidores;
    }

    public void setListaSeguidores(List<Seguidor> listaSeguidores) {
        this.listaSeguidores = listaSeguidores;
    }


}
