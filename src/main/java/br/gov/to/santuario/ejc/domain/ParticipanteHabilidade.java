package br.gov.to.santuario.ejc.domain;

import br.gov.to.santuario.seg.domain.Participante;
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
@Table(name = "participante_habilidade", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParticipanteHabilidade.findAll", query = "SELECT p FROM ParticipanteHabilidade p"),
    @NamedQuery(name = "ParticipanteHabilidade.findById", query = "SELECT p FROM ParticipanteHabilidade p WHERE p.id = :id")})
public class ParticipanteHabilidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "habilidade_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Habilidade habilidade;
    @JoinColumn(name = "participante_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Participante participante;

    public ParticipanteHabilidade() {
    }

    public ParticipanteHabilidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
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
        if (!(object instanceof ParticipanteHabilidade)) {
            return false;
        }
        ParticipanteHabilidade other = (ParticipanteHabilidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.ParticipanteHabilidade[ id=" + id + " ]";
    }
    
}
