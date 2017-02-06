package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "encontro_palestra", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncontroPalestra.findAll", query = "SELECT e FROM EncontroPalestra e"),
    @NamedQuery(name = "EncontroPalestra.findById", query = "SELECT e FROM EncontroPalestra e WHERE e.id = :id"),
    @NamedQuery(name = "EncontroPalestra.findByDataPalestra", query = "SELECT e FROM EncontroPalestra e WHERE e.dataPalestra = :dataPalestra"),
    @NamedQuery(name = "EncontroPalestra.findByHorarioPalestra", query = "SELECT e FROM EncontroPalestra e WHERE e.horarioPalestra = :horarioPalestra")})
public class EncontroPalestra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data_palestra")
    @Temporal(TemporalType.DATE)
    private Date dataPalestra;
    @Column(name = "horario_palestra")
    @Temporal(TemporalType.TIME)
    private Date horarioPalestra;
    @JoinColumn(name = "encontro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Encontro encontro;
    @JoinColumn(name = "palestrante_id", referencedColumnName = "id")
    @ManyToOne
    private Palestrante palestrante;
    @JoinColumn(name = "palestra_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Palestra palestras;

    public EncontroPalestra() {
    }

    public EncontroPalestra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataPalestra() {
        return dataPalestra;
    }

    public void setDataPalestra(Date dataPalestra) {
        this.dataPalestra = dataPalestra;
    }

    public Date getHorarioPalestra() {
        return horarioPalestra;
    }

    public void setHorarioPalestra(Date horarioPalestra) {
        this.horarioPalestra = horarioPalestra;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }

    public Palestrante getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
    }

    public Palestra getPalestras() {
        return palestras;
    }

    public void setPalestras(Palestra palestras) {
        this.palestras = palestras;
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
        if (!(object instanceof EncontroPalestra)) {
            return false;
        }
        EncontroPalestra other = (EncontroPalestra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.EncontroPalestra[ id=" + id + " ]";
    }
    
}
