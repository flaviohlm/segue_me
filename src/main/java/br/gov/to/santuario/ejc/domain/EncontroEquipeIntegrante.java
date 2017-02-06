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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "encontro_equipe_integrantes", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncontroEquipeIntegrante.findAll", query = "SELECT e FROM EncontroEquipeIntegrante e"),
    @NamedQuery(name = "EncontroEquipeIntegrante.findById", query = "SELECT e FROM EncontroEquipeIntegrante e WHERE e.id = :id"),
    @NamedQuery(name = "EncontroEquipeIntegrante.findByCoordenador", query = "SELECT e FROM EncontroEquipeIntegrante e WHERE e.coordenador = :coordenador")})
public class EncontroEquipeIntegrante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "coordenador")
    private boolean coordenador = false;
    @JoinColumn(name = "encontro_equipe_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private EncontroEquipe encontroEquipe;
    @JoinColumn(name = "seguidor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seguidor seguidor;

    public EncontroEquipeIntegrante() {
    }

    public EncontroEquipeIntegrante(Integer id) {
        this.id = id;
    }

    public EncontroEquipeIntegrante(Integer id, boolean coordenador) {
        this.id = id;
        this.coordenador = coordenador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(boolean coordenador) {
        this.coordenador = coordenador;
    }

    public EncontroEquipe getEncontroEquipe() {
        return encontroEquipe;
    }

    public void setEncontroEquipe(EncontroEquipe encontroEquipe) {
        this.encontroEquipe = encontroEquipe;
    }

    public Seguidor getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Seguidor seguidor) {
        this.seguidor = seguidor;
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
        if (!(object instanceof EncontroEquipeIntegrante)) {
            return false;
        }
        EncontroEquipeIntegrante other = (EncontroEquipeIntegrante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrantes[ id=" + id + " ]";
    }
    
}
