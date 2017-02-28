package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.EncontroPalestra;
import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.domain.Palestrante;
import br.gov.to.santuario.ejc.service.EncontroPalestraService;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.ejc.service.HabilidadeService;
import br.gov.to.santuario.ejc.service.PalestranteService;
import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.service.ParticipanteService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class PalestranteController implements Serializable {
    @ManagedProperty(value = "#{palestranteService}")
    private PalestranteService palestranteService;
    
    @ManagedProperty(value = "#{habilidadeService}")
    private HabilidadeService habilidadeService;
    
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    @ManagedProperty(value = "#{encontroPalestraService}")
    private EncontroPalestraService encontroPalestraService;
    
    @ManagedProperty(value = "#{participanteService}")
    private ParticipanteService participanteService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Palestrante palestrante = new Palestrante();
    private List<Palestrante> listaPalestrantes;
    private List<Habilidade> listaHabilidades;
    private List<Encontro> listaEncontro;
    private Palestrante palestranteSelecionado;
    private EncontroPalestra encontroPalestraSelecionada;    
    
    public void salvar(){        
        try{        
            if(palestrante.getParticipante().getDataCadastro() == null){
                palestrante.getParticipante().setDataCadastro(new Date());
            }
            if(palestrante.getParticipante().getApelido().equals("") || palestrante.getParticipante().getApelido() == null){
                String[] apelido = palestrante.getParticipante().getNome().split(" ");
                palestrante.getParticipante().setApelido(apelido[0]);
            }
            palestranteService.savePalestrante(palestrante);            
            messages.info("Palestrante salvo com sucesso!");
            palestrante = new Palestrante();            
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o palestrante.");
            e.printStackTrace();
        }
    }
    
    public String atualizar(){        
        try{                    
            if(palestrante.getParticipante().getApelido().equals("") || palestrante.getParticipante().getApelido() == null){
                String[] apelido = palestrante.getParticipante().getNome().split(" ");
                palestrante.getParticipante().setApelido(apelido[0]);
            }
            palestranteService.savePalestrante(palestrante);            
            messages.info("Palestrante salvo com sucesso!");
            palestrante = new Palestrante();            
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o palestrante.");
            e.printStackTrace();
            return "";
        }
        
        return "/segue-me/participantes/palestrantes/index.xhtml";
    }
    
    public void deletar(){
        try{            
            palestranteService.deletePalestrante(palestrante);
            listaPalestrantes = null;
            messages.info("Palestrante deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o palestrante.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            palestrante = palestranteService.findOnePalestrante(id);
            if(palestrante == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/participantes/palestrantes/cadastrar/index.xhtml?faces-redirect=true";
    }    

    public void adicionarPalestrante(){
        if(encontroPalestraSelecionada != null){
            encontroPalestraSelecionada.getPalestranteList().add(palestrante);
            palestrante = new Palestrante();
        }
    }
    
    public void salvarEncontroPalestra(){
        try{                    
            encontroPalestraService.saveEncontroPalestra(encontroPalestraSelecionada);            
            messages.info("Palestrante salva com sucesso!");                   
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar palestra.");
            e.printStackTrace();
        }
        
    }
    
    public void selecionarPalestrante(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/participantes/palestrantes/editar/index.xhtml?id=" + palestranteSelecionado.getId());
    }
    
    public List<String> find(String nome){
        nome = removeAcentos(nome);
        nome = nome.replaceAll(" ", "%");
        List<Participante> lista = participanteService.findByNome(nome);
        List<String> lista2 = new ArrayList<>();
        for(Participante obj : lista){
            lista2.add(obj.getNome());
        }
        
        return lista2;
    }
    
    public void onItemSelect(SelectEvent event) {        
        String nome = (String) event.getObject(); 
        
        Participante participante = participanteService.findByNomeExato(nome);
        
        Palestrante p = palestranteService.findByParticipante(participante.getId());
        
        if(p == null){
            participante.setNome(nome);
            palestrante.setParticipante(participante);
        }else{
            palestrante = p;
        }
    }
    
    public String removeAcentos(String s) {
        if (s == null) {
            return "";
        }
        String semAcentos = s.toLowerCase();
        semAcentos = semAcentos.replaceAll("[áàâãäa]", "_");
        semAcentos = semAcentos.replaceAll("[éèêëe]", "_");
        semAcentos = semAcentos.replaceAll("[íìîïi]", "_");
        semAcentos = semAcentos.replaceAll("[óòôõöo]", "_");
        semAcentos = semAcentos.replaceAll("[úùûüu]", "_");
        semAcentos = semAcentos.replaceAll("çc", "_");
        semAcentos = semAcentos.replaceAll("ñn", "_");
        return semAcentos;
    }
    
    //ver depois
    public void selecionarPalestra(){
        if(encontroPalestraSelecionada != null){
            listaPalestrantes = getListaPalestrantes();
            listaPalestrantes.removeAll(encontroPalestraSelecionada.getPalestranteList());
        }
    }
    
    //GETTERS AND SETTERS
    public PalestranteService getPalestranteService() {
        return palestranteService;
    }

    public void setPalestranteService(PalestranteService palestranteService) {
        this.palestranteService = palestranteService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Palestrante getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
    }

    public List<Palestrante> getListaPalestrantes() {
        if(listaPalestrantes == null){
            listaPalestrantes = palestranteService.findAllPalestrante();
        }
        return listaPalestrantes;
    }

    public void setListaPalestrantes(List<Palestrante> listaPalestrantes) {
        this.listaPalestrantes = listaPalestrantes;
    }

    public HabilidadeService getHabilidadeService() {
        return habilidadeService;
    }

    public void setHabilidadeService(HabilidadeService habilidadeService) {
        this.habilidadeService = habilidadeService;
    }

    public List<Habilidade> getListaHabilidades() {
        if(listaHabilidades == null){
            listaHabilidades = habilidadeService.findAllHabilidade();
        }
        return listaHabilidades;
    }

    public void setListaHabilidades(List<Habilidade> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }

    public EncontroService getEncontroService() {
        return encontroService;
    }

    public void setEncontroService(EncontroService encontroService) {
        this.encontroService = encontroService;
    }

    public List<Encontro> getListaEncontro() {
        if(listaEncontro == null){
            listaEncontro = encontroService.findAllEncontro();
        }
        return listaEncontro;
    }

    public void setListaEncontro(List<Encontro> listaEncontro) {
        this.listaEncontro = listaEncontro;
    }

    public EncontroPalestra getEncontroPalestraSelecionada() {
        return encontroPalestraSelecionada;
    }

    public void setEncontroPalestraSelecionada(EncontroPalestra encontroPalestraSelecionada) {
        this.encontroPalestraSelecionada = encontroPalestraSelecionada;
    }

    public EncontroPalestraService getEncontroPalestraService() {
        return encontroPalestraService;
    }

    public void setEncontroPalestraService(EncontroPalestraService encontroPalestraService) {
        this.encontroPalestraService = encontroPalestraService;
    }

    public Palestrante getPalestranteSelecionado() {
        return palestranteSelecionado;
    }

    public void setPalestranteSelecionado(Palestrante palestranteSelecionado) {
        this.palestranteSelecionado = palestranteSelecionado;
    }

    public ParticipanteService getParticipanteService() {
        return participanteService;
    }

    public void setParticipanteService(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }
   
}

