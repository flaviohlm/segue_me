package br.gov.to.santuario.ejc.domain;

import br.gov.to.santuario.seg.domain.Participante;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "seguimista", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seguimista.findAll", query = "SELECT s FROM Seguimista s"),
    @NamedQuery(name = "Seguimista.findById", query = "SELECT s FROM Seguimista s WHERE s.id = :id")})
public class Seguimista implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seguimista")
    private List<EncontroCirculoSeguimista> encontroCirculoSeguimistaList;
    
    @JoinColumn(name = "participante_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Participante participante;
    
    @JoinColumn(name = "encontro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Encontro encontro;

    public Seguimista() {
        this.participante = new Participante();
        this.encontro = new Encontro();
    }

    public Seguimista(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<EncontroCirculoSeguimista> getEncontroCirculoSeguimistaList() {
        return encontroCirculoSeguimistaList;
    }

    public void setEncontroCirculoSeguimistaList(List<EncontroCirculoSeguimista> encontroCirculoSeguimistaList) {
        this.encontroCirculoSeguimistaList = encontroCirculoSeguimistaList;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
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
        if (!(object instanceof Seguimista)) {
            return false;
        }
        Seguimista other = (Seguimista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Seguimista[ id=" + id + " ]";
    }
    
}
