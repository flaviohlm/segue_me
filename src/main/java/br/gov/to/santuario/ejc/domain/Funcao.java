package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "funcao", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcao.findAll", query = "SELECT f FROM Funcao f")
    , @NamedQuery(name = "Funcao.findById", query = "SELECT f FROM Funcao f WHERE f.id = :id")
    , @NamedQuery(name = "Funcao.findByDescricao", query = "SELECT f FROM Funcao f WHERE f.descricao = :descricao")
    , @NamedQuery(name = "Funcao.findByTabela", query = "SELECT f FROM Funcao f WHERE f.tabela = :tabela")})
public class Funcao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 100)
    @Column(name = "tabela")
    private String tabela;
    @OneToMany(mappedBy = "funcao")
    private List<EquipeDirigenteIntegrante> equipeDirigenteIntegranteList;

    public Funcao() {
    }

    public Funcao(Integer id) {
        this.id = id;
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

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    @XmlTransient
    public List<EquipeDirigenteIntegrante> getEquipeDirigenteIntegranteList() {
        return equipeDirigenteIntegranteList;
    }

    public void setEquipeDirigenteIntegranteList(List<EquipeDirigenteIntegrante> equipeDirigenteIntegranteList) {
        this.equipeDirigenteIntegranteList = equipeDirigenteIntegranteList;
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
        if (!(object instanceof Funcao)) {
            return false;
        }
        Funcao other = (Funcao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.Funcao[ id=" + id + " ]";
    }
    
}
