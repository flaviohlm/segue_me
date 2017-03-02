package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.domain.Palestrante;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.HabilidadeService;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.service.ParticipanteService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class SeguidorController implements Serializable {
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{habilidadeService}")
    private HabilidadeService habilidadeService;
    
    @ManagedProperty(value = "#{participanteService}")
    private ParticipanteService participanteService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Seguidor seguidor = new Seguidor();
    private List<Habilidade> listaHabilidades;
    private Seguidor seguidorSelecionado;
    private LazyDataModel<Seguidor> listaObjetosLazy;
    
    @PostConstruct
    public void init() {        
        // Classe interna anônima: ao mesmo tempo em que está instanciando, muda o comportamento interno da classe LazyDataModel (herança>>polimorfismo)        
        listaObjetosLazy = new LazyDataModel<Seguidor>() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public List<Seguidor> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                String globalFilter = removeAcentos((String) filters.get("globalFilter"));
                
                //Variavel de retorno
                List<Seguidor> result;
                //Seta o tamanho da pagina
                this.setPageSize(pageSize);

                if (sortOrder == SortOrder.UNSORTED || StringUtils.isBlank(sortField)) {
                    //Atribui para o request em qual pagina vai e o tamanho da pagina                    
                    PageRequest request = new PageRequest(first / pageSize, pageSize);
                    //Faz a consulta no banco passando o request e o os filtros para o service
                    Page<Seguidor> page = seguidorService.findAll(request, globalFilter);
                    //Atribui a quantidade de registro total
                    this.setRowCount((int) page.getTotalElements());
                    // Pega a lista de registro que irá mostrar na pagina
                    result = page.getContent();
                } else {
                    Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

                    PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                    Page<Seguidor> page = seguidorService.findAll(request, globalFilter);
                    this.setRowCount((int) page.getTotalElements());
                    result = page.getContent();
                }
                // retorna a lista com os registro
                return result;
            }

            @Override
            public Object getRowKey(Seguidor object) {
                return object.getId().toString();
            }

            @Override
            public Seguidor getRowData(String rowKey) {
                return seguidorService.findOneSeguidor(Integer.parseInt(rowKey));
            }
        };
               
    }
    
    
    
    public void salvar(){        
        try{
            if(seguidor.getParticipante().getDataCadastro() == null){
                seguidor.getParticipante().setDataCadastro(new Date());
            }
            if(seguidor.getParticipante().getApelido() == null || seguidor.getParticipante().getApelido().equals("")){
                String[] apelido = seguidor.getParticipante().getNome().split(" ");
                seguidor.getParticipante().setApelido(apelido[0]);
            }
            seguidorService.saveSeguidor(seguidor);            
            messages.info("Seguidor salvo com sucesso!");
            seguidor = new Seguidor();
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o seguidor.");
            e.printStackTrace();
        }
    }
    
    public String atualizar(){        
        try{           
            if(seguidor.getParticipante().getApelido().equals("") || seguidor.getParticipante().getApelido() == null){
                String[] apelido = seguidor.getParticipante().getNome().split(" ");
                seguidor.getParticipante().setApelido(apelido[0]);
            }
            seguidorService.saveSeguidor(seguidor);            
            messages.info("Seguidor salvo com sucesso!");
            seguidor = new Seguidor();
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o seguidor.");
            e.printStackTrace();
            return "";
        }
        return "/segue-me/participantes/seguidores/index.xhtml";
    }
    
    public void deletar(){
        try{            
            seguidorService.deleteSeguidor(seguidor);            
            messages.info("Seguidor deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o seguidor.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            seguidor = seguidorService.findOneSeguidor(id);
            if(seguidor == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/participantes/seguidores/cadastrar/index.xhtml?faces-redirect=true";
    }    

    public void selecionarSeguidor(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/participantes/seguidores/editar/index.xhtml?id=" + seguidorSelecionado.getId());
    }
    
    public void onItemSelect(SelectEvent event) {
        String nome = (String) event.getObject(); 
        
        Participante participante = participanteService.findByNomeExato(nome);        
        
        Seguidor s = seguidorService.findByParticipante(participante.getId());
        
        if(s == null){
            participante.setNome(nome);
            seguidor.setParticipante(participante);
        }else{
            seguidor = s;
        }
    }
    
    public String removeAcentos(String s) {
        if (s == null) {
            return s;
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
    
    public void verificarTelefone(){
        List<Participante> lista = participanteService.findAllParticipantes();
        
        for(Participante p : lista){
            if(p.getTelefoneCelular() != null){
                System.out.println("Antes: "+p.getTelefoneCelular());
                p.setTelefoneCelular(p.getTelefoneCelular().replaceAll("[^0-9]", "").trim());
                System.out.println("LENGHT: "+p.getTelefoneCelular().length());
                 if(p.getTelefoneCelular().length() == 9){//81143586
                    p.setTelefoneCelular("(63) "+p.getTelefoneCelular().substring(0, 4)+"-"+p.getTelefoneCelular().substring(4));
                    System.out.println("Depois: "+p.getTelefoneCelular());                    
                }
                else{
                    System.out.println("----------------");
                }
                System.out.println("");
            }
        }
        
        //participanteService.saveParticipante(lista);
        
    }
    
    public void atualizarTelefone(){
        List<Participante> lista = participanteService.findAllParticipantes();
        
        for(Participante p : lista){
            if(p.getTelefoneCelular() != null){
                
                String telefoneTrim = p.getTelefoneCelular().replaceAll("[^0-9]", "").trim();
                
                if(telefoneTrim.length() == 9){//981143586
                    p.setTelefoneCelular("(63) "+telefoneTrim.substring(0, 5)+"-"+telefoneTrim.substring(5));                                       
                }
            }
        }
        
       // participanteService.saveParticipante(lista);
        
    }
    
    public void copiar(){
        //LEMBRAR DE COMENTAR O CASCADE EM SEGUIDOR PRA NAO DUPLICAR OS PARTICIPANTES
        List<Participante> lista = participanteService.findAllParticipantes2();
        
        Seguidor s;
        List<Seguidor> listaS = new ArrayList<>();
        for(Participante p : lista){
            System.out.println(p.getNome());
        }

    }
    
    public void copiar2(){
        //LEMBRAR DE COMENTAR O CASCADE EM SEGUIDOR PRA NAO DUPLICAR OS PARTICIPANTES
        List<Participante> lista = participanteService.findAllParticipantes2();
        
        Seguidor s;
        List<Seguidor> listaS = new ArrayList<>();
        for(Participante p : lista){
            s = new Seguidor();
            s.setTio(true);
            s.setParticipante(p);
            //seguidorService.saveSeguidor(s);
        }

    }
    
    //GETTERS AND SETTERS
    public SeguidorService getSeguidorService() {
        return seguidorService;
    }

    public void setSeguidorService(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
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

    public Seguidor getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Seguidor seguidor) {
        this.seguidor = seguidor;
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

    public Seguidor getSeguidorSelecionado() {
        return seguidorSelecionado;
    }

    public void setSeguidorSelecionado(Seguidor seguidorSelecionado) {
        this.seguidorSelecionado = seguidorSelecionado;
    }

    public ParticipanteService getParticipanteService() {
        return participanteService;
    }

    public void setParticipanteService(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    public LazyDataModel<Seguidor> getListaObjetosLazy() {
        return listaObjetosLazy;
    }

    public void setListaObjetosLazy(LazyDataModel<Seguidor> listaObjetosLazy) {
        this.listaObjetosLazy = listaObjetosLazy;
    }
    
}

