package br.gov.to.santuario.seg.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "perfil_usuario", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilUsuario.findAll", query = "SELECT p FROM PerfilUsuario p"),
    @NamedQuery(name = "PerfilUsuario.findByCpf", query = "SELECT pu FROM PerfilUsuario pu WHERE pu.participante.cpf = :cpf"),
    @NamedQuery(name = "PerfilUsuario.findById", query = "SELECT p FROM PerfilUsuario p WHERE p.id = :id")})
public class PerfilUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Perfil perfil;
    
    @JoinColumn(name = "participante_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Participante participante;

    @ManyToMany
    @JoinTable(schema = "segueme", name = "perfil_usuario", joinColumns = {
            @JoinColumn(name = "perfil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "participante_id", referencedColumnName = "id")})
    private List<Perfil> perfis;
    
    @ManyToMany    
    @JoinTable(schema = "segueme", name = "perfil_usuario", joinColumns = {
            @JoinColumn(name = "perfil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", referencedColumnName = "id")})
    @OrderBy(value="nivel ASC, ordem ASC, descricao ASC")
    private List<Menu> menus;
    
    public PerfilUsuario() {
    }

    public PerfilUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
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
        if (!(object instanceof PerfilUsuario)) {
            return false;
        }
        PerfilUsuario other = (PerfilUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.secad.seg.domain.PerfilUsuario[ id=" + id + " ]";
    }
    
}
