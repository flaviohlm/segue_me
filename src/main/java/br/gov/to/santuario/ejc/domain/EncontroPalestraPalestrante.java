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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "encontro_palestra_palestrante", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncontroPalestraPalestrante.findAll", query = "SELECT e FROM EncontroPalestraPalestrante e"),
    @NamedQuery(name = "EncontroPalestraPalestrante.findById", query = "SELECT e FROM EncontroPalestraPalestrante e WHERE e.id = :id")})
public class EncontroPalestraPalestrante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "encontro_palestra_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private EncontroPalestra encontroPalestra;
    
    @JoinColumn(name = "palestrante_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Palestrante palestrante;

    public EncontroPalestraPalestrante() {
    }

    public EncontroPalestraPalestrante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EncontroPalestra getEncontroPalestra() {
        return encontroPalestra;
    }

    public void setEncontroPalestra(EncontroPalestra encontroPalestra) {
        this.encontroPalestra = encontroPalestra;
    }

    public Palestrante getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
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
        if (!(object instanceof EncontroPalestraPalestrante)) {
            return false;
        }
        EncontroPalestraPalestrante other = (EncontroPalestraPalestrante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.EncontroPalestraPalestrante[ id=" + id + " ]";
    }
    
}
