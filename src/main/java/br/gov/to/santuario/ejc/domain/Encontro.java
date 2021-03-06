package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "encontro", schema="segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encontro.findAll", query = "SELECT e FROM Encontro e"),
    @NamedQuery(name = "Encontro.findById", query = "SELECT e FROM Encontro e WHERE e.id = :id"),
    @NamedQuery(name = "Encontro.findByDescricao", query = "SELECT e FROM Encontro e WHERE e.descricao = :descricao"),
    @NamedQuery(name = "Encontro.findByDataRealizacaoInicio", query = "SELECT e FROM Encontro e WHERE e.dataRealizacaoInicio = :dataRealizacaoInicio"),
    @NamedQuery(name = "Encontro.findByDataRealizacaoFim", query = "SELECT e FROM Encontro e WHERE e.dataRealizacaoFim = :dataRealizacaoFim"),
    @NamedQuery(name = "Encontro.findByFinalizado", query = "SELECT e FROM Encontro e WHERE e.finalizado = :finalizado")})
public class Encontro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "data_realizacao_inicio")
    @Temporal(TemporalType.DATE)
    private Date dataRealizacaoInicio;
    @Column(name = "data_realizacao_fim")
    @Temporal(TemporalType.DATE)
    private Date dataRealizacaoFim;
   
    @Column(name = "imagem_padroeira")
    private byte[] imagemPadroeira;
    @Column(name = "finalizado")
    private Boolean finalizado = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encontro")    
    private List<EncontroCirculo> encontroCirculoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encontro")
    private List<EncontroParoquia> encontroParoquiaList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encontro")
    @OrderBy(value="dataPalestra, horarioPalestra")
    private List<EncontroPalestra> encontroPalestraList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encontro")        
    private List<EncontroEquipe> encontroEquipeList;
    
    @JoinColumn(name = "equipe_dirigente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EquipeDirigente equipeDirigente;

    @JoinColumn(name = "conselho_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Conselho conselho;
    
    @JoinColumn(name = "paroquia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Paroquia paroquia;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "encontro_equipe", joinColumns = {
            @JoinColumn(name = "encontro_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "equipe_id", referencedColumnName = "ID")})
    private List<Equipe> equipeList;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "encontro_circulo", joinColumns = {
            @JoinColumn(name = "encontro_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "circulo_id", referencedColumnName = "ID")})
    private List<Circulo> circuloList;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "encontro_palestra", joinColumns = {
            @JoinColumn(name = "encontro_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "palestra_id", referencedColumnName = "ID")})
    private List<Palestra> palestraList;
    
    
    public Encontro() {
    }

    public Encontro(Integer id) {
        this.id = id;
    }

    public Encontro(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataRealizacaoInicio() {
        return dataRealizacaoInicio;
    }

    public void setDataRealizacaoInicio(Date dataRealizacaoInicio) {
        this.dataRealizacaoInicio = dataRealizacaoInicio;
    }

    public Date getDataRealizacaoFim() {
        return dataRealizacaoFim;
    }

    public void setDataRealizacaoFim(Date dataRealizacaoFim) {
        this.dataRealizacaoFim = dataRealizacaoFim;
    }

    public byte[] getImagemPadroeira() {
        return imagemPadroeira;
    }

    public void setImagemPadroeira(byte[] imagemPadroeira) {
        this.imagemPadroeira = imagemPadroeira;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public List<EncontroCirculo> getEncontroCirculoList() {
        return encontroCirculoList;
    }

    public void setEncontroCirculoList(List<EncontroCirculo> encontroCirculoList) {
        this.encontroCirculoList = encontroCirculoList;
    }

    public List<EncontroParoquia> getEncontroParoquiaList() {
        return encontroParoquiaList;
    }

    public void setEncontroParoquiaList(List<EncontroParoquia> encontroParoquiaList) {
        this.encontroParoquiaList = encontroParoquiaList;
    }

    public List<EncontroPalestra> getEncontroPalestraList() {
        return encontroPalestraList;
    }

    public void setEncontroPalestraList(List<EncontroPalestra> encontroPalestraList) {
        this.encontroPalestraList = encontroPalestraList;
    }

    public List<EncontroEquipe> getEncontroEquipeList() {
        return encontroEquipeList;
    }

    public void setEncontroEquipeList(List<EncontroEquipe> encontroEquipeList) {
        this.encontroEquipeList = encontroEquipeList;
    }

    public List<Equipe> getEquipeList() {
        return equipeList;
    }

    public void setEquipeList(List<Equipe> equipeList) {
        this.equipeList = equipeList;
    }

    public List<Circulo> getCirculoList() {
        return circuloList;
    }

    public void setCirculoList(List<Circulo> circuloList) {
        this.circuloList = circuloList;
    }

    public List<Palestra> getPalestraList() {
        return palestraList;
    }

    public void setPalestraList(List<Palestra> palestraList) {
        this.palestraList = palestraList;
    }

    public EquipeDirigente getEquipeDirigente() {
        return equipeDirigente;
    }

    public void setEquipeDirigente(EquipeDirigente equipeDirigente) {
        this.equipeDirigente = equipeDirigente;
    }

    public Conselho getConselho() {
        return conselho;
    }

    public void setConselho(Conselho conselho) {
        this.conselho = conselho;
    }

    public Paroquia getParoquia() {
        return paroquia;
    }

    public void setParoquia(Paroquia paroquia) {
        this.paroquia = paroquia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encontro)) {
            return false;
        }
        Encontro other = (Encontro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Encontro[ id=" + id + " ]";
    }
    
}
