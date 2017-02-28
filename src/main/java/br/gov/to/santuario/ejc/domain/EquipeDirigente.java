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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "equipe_dirigente", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipeDirigente.findAll", query = "SELECT e FROM EquipeDirigente e")
    , @NamedQuery(name = "EquipeDirigente.findById", query = "SELECT e FROM EquipeDirigente e WHERE e.id = :id")
    , @NamedQuery(name = "EquipeDirigente.findByDataInicio", query = "SELECT e FROM EquipeDirigente e WHERE e.dataInicio = :dataInicio")
    , @NamedQuery(name = "EquipeDirigente.findByDataFim", query = "SELECT e FROM EquipeDirigente e WHERE e.dataFim = :dataFim")})
public class EquipeDirigente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "data_inicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "data_fim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;

    @JoinColumn(name = "diretor_espiritual_id", referencedColumnName = "id")
    @ManyToOne
    private DiretorEspiritual diretorEspiritual;
    
    @JoinColumn(name = "paroquia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Paroquia paroquia;
    
    @OneToMany(mappedBy = "equipeDirigente", cascade = CascadeType.ALL)
    private List<EquipeDirigenteIntegrante> equipeDirigenteIntegranteList;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "equipe_dirigente_integrante", joinColumns = {
            @JoinColumn(name = "equipe_dirigente_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "seguidor_id", referencedColumnName = "ID")})
    private List<Seguidor> seguidorList;
    
    public EquipeDirigente() {
        diretorEspiritual = new DiretorEspiritual();
        paroquia = new Paroquia();
    }

    public EquipeDirigente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public DiretorEspiritual getDiretorEspiritual() {
        return diretorEspiritual;
    }

    public void setDiretorEspiritual(DiretorEspiritual diretorEspiritual) {
        this.diretorEspiritual = diretorEspiritual;
    }

    public List<EquipeDirigenteIntegrante> getEquipeDirigenteIntegranteList() {
        return equipeDirigenteIntegranteList;
    }

    public void setEquipeDirigenteIntegranteList(List<EquipeDirigenteIntegrante> equipeDirigenteIntegranteList) {
        this.equipeDirigenteIntegranteList = equipeDirigenteIntegranteList;
    }

    public List<Seguidor> getSeguidorList() {
        return seguidorList;
    }

    public void setSeguidorList(List<Seguidor> seguidorList) {
        this.seguidorList = seguidorList;
    }

    public Paroquia getParoquia() {
        return paroquia;
    }

    public void setParoquia(Paroquia paroquia) {
        this.paroquia = paroquia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof EquipeDirigente)) {
            return false;
        }
        EquipeDirigente other = (EquipeDirigente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.EquipeDirigente[ id=" + id + " ]";
    }
    
}
