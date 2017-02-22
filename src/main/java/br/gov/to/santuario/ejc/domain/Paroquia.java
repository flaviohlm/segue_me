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
@Table(name = "paroquia", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paroquia.findAll", query = "SELECT p FROM Paroquia p"),
    @NamedQuery(name = "Paroquia.findById", query = "SELECT p FROM Paroquia p WHERE p.id = :id"),
    @NamedQuery(name = "Paroquia.findByDescricao", query = "SELECT p FROM Paroquia p WHERE p.descricao = :descricao")})
public class Paroquia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "imagem")
    private byte[] imagem;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paroquia")
    private List<EncontroParoquia> encontroParoquiaList;

    public Paroquia() {
    }

    public Paroquia(Integer id) {
        this.id = id;
    }

    public Paroquia(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<EncontroParoquia> getEncontroParoquiaList() {
        return encontroParoquiaList;
    }

    public void setEncontroParoquiaList(List<EncontroParoquia> encontroParoquiaList) {
        this.encontroParoquiaList = encontroParoquiaList;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
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
        if (!(object instanceof Paroquia)) {
            return false;
        }
        Paroquia other = (Paroquia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Paroquia[ id=" + id + " ]";
    }
    
}
