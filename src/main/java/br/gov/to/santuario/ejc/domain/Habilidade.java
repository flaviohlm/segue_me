package br.gov.to.santuario.ejc.domain;

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
@Table(name = "habilidade", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Habilidade.findAll", query = "SELECT h FROM Habilidade h"),
    @NamedQuery(name = "Habilidade.findById", query = "SELECT h FROM Habilidade h WHERE h.id = :id"),
    @NamedQuery(name = "Habilidade.findByDescricao", query = "SELECT h FROM Habilidade h WHERE h.descricao = :descricao")})
public class Habilidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao")
    private String descricao;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "habilidade", fetch = FetchType.LAZY)
    private List<ParticipanteHabilidade> participanteHabilidadeList;

    public Habilidade() {
    }

    public Habilidade(Integer id) {
        this.id = id;
    }

    public Habilidade(Integer id, String descricao) {
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
    public List<ParticipanteHabilidade> getParticipanteHabilidadeList() {
        return participanteHabilidadeList;
    }

    public void setParticipanteHabilidadeList(List<ParticipanteHabilidade> participanteHabilidadeList) {
        this.participanteHabilidadeList = participanteHabilidadeList;
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
        if (!(object instanceof Habilidade)) {
            return false;
        }
        Habilidade other = (Habilidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Habilidade[ id=" + id + " ]";
    }
    
}
