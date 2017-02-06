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
@Table(name = "palestrante", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palestrante.findAll", query = "SELECT p FROM Palestrante p"),
    @NamedQuery(name = "Palestrante.findById", query = "SELECT p FROM Palestrante p WHERE p.id = :id")})
public class Palestrante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "palestrante")
    private List<EncontroPalestra> encontroPalestraList;
    @JoinColumn(name = "participante_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Participante participante;

    public Palestrante() {
        this.participante = new Participante();
    }

    public Palestrante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<EncontroPalestra> getEncontroPalestraList() {
        return encontroPalestraList;
    }

    public void setEncontroPalestraList(List<EncontroPalestra> encontroPalestraList) {
        this.encontroPalestraList = encontroPalestraList;
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
        if (!(object instanceof Palestrante)) {
            return false;
        }
        Palestrante other = (Palestrante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Palestrante[ id=" + id + " ]";
    }
    
}
