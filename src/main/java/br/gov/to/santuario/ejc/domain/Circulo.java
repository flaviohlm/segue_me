package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "circulo", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Circulo.findAll", query = "SELECT c FROM Circulo c"),
    @NamedQuery(name = "Circulo.findById", query = "SELECT c FROM Circulo c WHERE c.id = :id")})
public class Circulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "apostolo")
    private String apostolo;
    
    @Column(name = "cor")
    private String cor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "circulo")
    private List<EncontroCirculo> encontroCirculoList;

    public Circulo() {
    }

    public Circulo(Integer id) {
        this.id = id;
    }

    public Circulo(Integer id, String descricao) {
        this.id = id;
        this.apostolo = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApostolo() {
        return apostolo;
    }

    public void setApostolo(String apostolo) {
        this.apostolo = apostolo;
    }

    @XmlTransient
    public List<EncontroCirculo> getEncontroCirculoList() {
        return encontroCirculoList;
    }

    public void setEncontroCirculoList(List<EncontroCirculo> encontroCirculoList) {
        this.encontroCirculoList = encontroCirculoList;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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
        if (!(object instanceof Circulo)) {
            return false;
        }
        Circulo other = (Circulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Circulo[ id=" + id + " ]";
    }
    
}
