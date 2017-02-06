package br.gov.to.santuario.seg.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(catalog = "postgres", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfil.findById", query = "SELECT p FROM Perfil p WHERE p.id = :id"),
    @NamedQuery(name = "Perfil.findByNome", query = "SELECT p FROM Perfil p WHERE p.nome = :nome"),
    @NamedQuery(name = "Perfil.findByNivel", query = "SELECT p FROM Perfil p WHERE p.nivel = :nivel")})
public class Perfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "nivel")
    private Integer nivel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<MenuUsuario> menuUsuarioList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<PerfilUsuario> perfilUsuarioList;

    @ManyToMany
    @JoinTable(schema = "segueme", name = "menu_perfil", joinColumns = {
            @JoinColumn(name = "perfil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", referencedColumnName = "id")})
    private List<Menu> menus;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "perfil_usuario", joinColumns = {
            @JoinColumn(name = "perfil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "usuario_id", referencedColumnName = "id")})
    private List<Participante> usuarios;
        
    
    public Perfil() {
    }

    public Perfil(Integer id) {
        this.id = id;
    }

    public Perfil(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @XmlTransient
    public List<MenuUsuario> getMenuUsuarioList() {
        return menuUsuarioList;
    }

    public void setMenuUsuarioList(List<MenuUsuario> menuUsuarioList) {
        this.menuUsuarioList = menuUsuarioList;
    }

    @XmlTransient
    public List<PerfilUsuario> getPerfilUsuarioList() {
        return perfilUsuarioList;
    }

    public void setPerfilUsuarioList(List<PerfilUsuario> perfilUsuarioList) {
        this.perfilUsuarioList = perfilUsuarioList;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Participante> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Participante> usuarios) {
        this.usuarios = usuarios;
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
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.secad.seg.domain.Perfil[ id=" + id + " ]";
    }
    
}
