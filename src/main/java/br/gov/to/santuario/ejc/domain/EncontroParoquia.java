package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "encontro_paroquia", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncontroParoquia.findAll", query = "SELECT e FROM EncontroParoquia e"),
    @NamedQuery(name = "EncontroParoquia.findById", query = "SELECT e FROM EncontroParoquia e WHERE e.id = :id")})
public class EncontroParoquia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "encontro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Encontro encontro;
    @JoinColumn(name = "paroquia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Paroquia paroquia;

    public EncontroParoquia() {
    }

    public EncontroParoquia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
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
        if (!(object instanceof EncontroParoquia)) {
            return false;
        }
        EncontroParoquia other = (EncontroParoquia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.EncontroParoquia[ id=" + id + " ]";
    }
    
}
