package br.gov.to.santuario.ejc.domain;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "equipe_dirigente_integrante", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipeDirigenteIntegrante.findAll", query = "SELECT e FROM EquipeDirigenteIntegrante e")
    , @NamedQuery(name = "EquipeDirigenteIntegrante.findById", query = "SELECT e FROM EquipeDirigenteIntegrante e WHERE e.id = :id")})
public class EquipeDirigenteIntegrante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "funcao_id", referencedColumnName = "id")
    @ManyToOne
    private Funcao funcao;
    
    @JoinColumn(name = "equipe_dirigente_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private EquipeDirigente equipeDirigente;
    
    @JoinColumn(name = "seguidor_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private Seguidor seguidor;

    public EquipeDirigenteIntegrante() {
    }

    public EquipeDirigenteIntegrante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public EquipeDirigente getEquipeDirigente() {
        return equipeDirigente;
    }

    public void setEquipeDirigente(EquipeDirigente equipeDirigente) {
        this.equipeDirigente = equipeDirigente;
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
        if (!(object instanceof EquipeDirigenteIntegrante)) {
            return false;
        }
        EquipeDirigenteIntegrante other = (EquipeDirigenteIntegrante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.EquipeDirigenteIntegrante[ id=" + id + " ]";
    }
    
}
