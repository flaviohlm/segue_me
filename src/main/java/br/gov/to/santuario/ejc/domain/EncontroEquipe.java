package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "encontro_equipe", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncontroEquipe.findAll", query = "SELECT e FROM EncontroEquipe e"),
    @NamedQuery(name = "EncontroEquipe.findById", query = "SELECT e FROM EncontroEquipe e WHERE e.id = :id")})
public class EncontroEquipe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "encontroEquipe")
    private List<EncontroEquipeIntegrante> encontroEquipeIntegrantesList;
    @JoinColumn(name = "encontro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Encontro encontro;
    @JoinColumn(name = "equipe_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipe equipe;

    @ManyToMany
    @JoinTable(schema = "segueme", name = "encontro_equipe_integrantes", joinColumns = {
            @JoinColumn(name = "encontro_equipe_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "seguidor_id", referencedColumnName = "ID")})
    private List<Seguidor> seguidorList;
    
    public EncontroEquipe() {
    }

    public EncontroEquipe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<EncontroEquipeIntegrante> getEncontroEquipeIntegrantesList() {
        return encontroEquipeIntegrantesList;
    }

    public void setEncontroEquipeIntegrantesList(List<EncontroEquipeIntegrante> encontroEquipeIntegrantesList) {
        this.encontroEquipeIntegrantesList = encontroEquipeIntegrantesList;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Seguidor> getSeguidorList() {
        return seguidorList;
    }

    public void setSeguidorList(List<Seguidor> seguidorList) {
        this.seguidorList = seguidorList;
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
        if (!(object instanceof EncontroEquipe)) {
            return false;
        }
        EncontroEquipe other = (EncontroEquipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.secad.ergon.domain.EncontroEquipe[ id=" + id + " ]";
    }
    
}
