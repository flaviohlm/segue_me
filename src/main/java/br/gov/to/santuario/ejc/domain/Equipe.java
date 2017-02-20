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
@Table(name = "equipe", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipe.findAll", query = "SELECT e FROM Equipe e"),
    @NamedQuery(name = "Equipe.findById", query = "SELECT e FROM Equipe e WHERE e.id = :id"),
    @NamedQuery(name = "Equipe.findByDescricao", query = "SELECT e FROM Equipe e WHERE e.descricao = :descricao")})
public class Equipe implements Serializable {
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipe", fetch = FetchType.LAZY)
    private List<EncontroEquipe> encontroEquipeList;
    
    @Column(name = "ordem_apresentacao")
    private Integer ordemApresentacao;
    
    @Column(name = "ordem_quadrante")
    private Integer ordemQuadrante;
    
    @Column(name = "quantidade_participantes")
    private Integer quantidadeParticipantes;

    public Equipe() {
    }

    public Equipe(Integer id) {
        this.id = id;
    }

    public Equipe(Integer id, String descricao) {
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
    public List<EncontroEquipe> getEncontroEquipeList() {
        return encontroEquipeList;
    }

    public void setEncontroEquipeList(List<EncontroEquipe> encontroEquipeList) {
        this.encontroEquipeList = encontroEquipeList;
    }

    public Integer getQuantidadeParticipantes() {
        return quantidadeParticipantes;
    }

    public void setQuantidadeParticipantes(Integer quantidadeParticipantes) {
        this.quantidadeParticipantes = quantidadeParticipantes;
    }

    public Integer getOrdemApresentacao() {
        return ordemApresentacao;
    }

    public void setOrdemApresentacao(Integer ordemApresentacao) {
        this.ordemApresentacao = ordemApresentacao;
    }

    public Integer getOrdemQuadrante() {
        return ordemQuadrante;
    }

    public void setOrdemQuadrante(Integer ordemQuadrante) {
        this.ordemQuadrante = ordemQuadrante;
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
        if (!(object instanceof Equipe)) {
            return false;
        }
        Equipe other = (Equipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Equipe[ id=" + id + " ]";
    }
    
}
