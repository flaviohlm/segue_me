package br.gov.to.santuario.ejc.domain;

import br.gov.to.santuario.seg.domain.Participante;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.JoinFetch;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "seguidor", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seguidor.findAll", query = "SELECT s FROM Seguidor s"),
    @NamedQuery(name = "Seguidor.findById", query = "SELECT s FROM Seguidor s WHERE s.id = :id"),
    @NamedQuery(name = "Seguidor.findByTio", query = "SELECT s FROM Seguidor s WHERE s.tio = :tio")})
public class Seguidor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "tio")
    private boolean tio = false;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seguidorPadrinho", fetch = FetchType.LAZY)
    private List<EncontroCirculo> encontroCirculoList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seguidorMadrinha", fetch = FetchType.LAZY)
    private List<EncontroCirculo> encontroCirculoList1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seguidor", fetch = FetchType.LAZY)
    private List<EncontroEquipeIntegrante> encontroEquipeIntegrantesList;
        
    @JoinColumn(name = "participante_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Participante participante;

    public Seguidor() {
        this.participante = new Participante();
    }

    public Seguidor(Integer id) {
        this.id = id;
    }

    public Seguidor(Integer id, boolean tio) {
        this.id = id;
        this.tio = tio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getTio() {
        return tio;
    }

    public void setTio(boolean tio) {
        this.tio = tio;
    }

    @XmlTransient
    public List<EncontroCirculo> getEncontroCirculoList() {
        return encontroCirculoList;
    }

    public void setEncontroCirculoList(List<EncontroCirculo> encontroCirculoList) {
        this.encontroCirculoList = encontroCirculoList;
    }

    @XmlTransient
    public List<EncontroCirculo> getEncontroCirculoList1() {
        return encontroCirculoList1;
    }

    public void setEncontroCirculoList1(List<EncontroCirculo> encontroCirculoList1) {
        this.encontroCirculoList1 = encontroCirculoList1;
    }

    @XmlTransient
    public List<EncontroEquipeIntegrante> getEncontroEquipeIntegrantesList() {
        return encontroEquipeIntegrantesList;
    }

    public void setEncontroEquipeIntegrantesList(List<EncontroEquipeIntegrante> encontroEquipeIntegrantesList) {
        this.encontroEquipeIntegrantesList = encontroEquipeIntegrantesList;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
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
        if (!(object instanceof Seguidor)) {
            return false;
        }
        Seguidor other = (Seguidor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Seguidor[ id=" + id + " ]";
    }
    
}
