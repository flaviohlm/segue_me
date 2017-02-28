package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "conselho", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conselho.findAll", query = "SELECT c FROM Conselho c")
    , @NamedQuery(name = "Conselho.findById", query = "SELECT c FROM Conselho c WHERE c.id = :id")
    , @NamedQuery(name = "Conselho.findByDataInicio", query = "SELECT c FROM Conselho c WHERE c.dataInicio = :dataInicio")
    , @NamedQuery(name = "Conselho.findByDataFim", query = "SELECT c FROM Conselho c WHERE c.dataFim = :dataFim")})
public class Conselho implements Serializable {

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
    
    @OneToMany(mappedBy = "conselho")
    private List<ConselhoIntegrante> conselhoIntegranteList;

    @JoinColumn(name = "diretor_espiritual_id", referencedColumnName = "id")
    @ManyToOne
    private DiretorEspiritual diretorEspiritual;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "conselho_integrante", joinColumns = {
            @JoinColumn(name = "conselho_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "seguidor_id", referencedColumnName = "ID")})
    private List<Seguidor> seguidorList;
    
    public Conselho() {
        diretorEspiritual = new DiretorEspiritual();
    }

    public Conselho(Integer id) {
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

    @XmlTransient
    public List<ConselhoIntegrante> getConselhoIntegranteList() {
        return conselhoIntegranteList;
    }

    public void setConselhoIntegranteList(List<ConselhoIntegrante> conselhoIntegranteList) {
        this.conselhoIntegranteList = conselhoIntegranteList;
    }

    public DiretorEspiritual getDiretorEspiritual() {
        return diretorEspiritual;
    }

    public void setDiretorEspiritual(DiretorEspiritual diretorEspiritual) {
        this.diretorEspiritual = diretorEspiritual;
    }

    public List<Seguidor> getSeguidorList() {
        return seguidorList;
    }

    public void setSeguidorList(List<Seguidor> seguidorList) {
        this.seguidorList = seguidorList;
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
        if (!(object instanceof Conselho)) {
            return false;
        }
        Conselho other = (Conselho) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Conselho[ id=" + id + " ]";
    }
    
}
