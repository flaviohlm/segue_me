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
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findById", query = "SELECT m FROM Menu m WHERE m.id = :id"),
    @NamedQuery(name = "Menu.findByDescricao", query = "SELECT m FROM Menu m WHERE m.descricao = :descricao"),
    @NamedQuery(name = "Menu.findByObservacoes", query = "SELECT m FROM Menu m WHERE m.observacoes = :observacoes"),
    @NamedQuery(name = "Menu.findByExcluido", query = "SELECT m FROM Menu m WHERE m.excluido = :excluido"),
    @NamedQuery(name = "Menu.findByOrdem", query = "SELECT m FROM Menu m WHERE m.ordem = :ordem"),
    @NamedQuery(name = "Menu.findByAtivo", query = "SELECT m FROM Menu m WHERE m.ativo = :ativo"),
    @NamedQuery(name = "Menu.findByNivel", query = "SELECT m FROM Menu m WHERE m.nivel = :nivel"),
    @NamedQuery(name = "Menu.findByUrl", query = "SELECT m FROM Menu m WHERE m.url = :url"),
    @NamedQuery(name = "Menu.findByIcone", query = "SELECT m FROM Menu m WHERE m.icone = :icone")})
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "descricao")
    private String descricao;
   
    @Column(name = "observacoes")
    private String observacoes;
   
    @Column(name = "excluido")
    private Boolean excluido=false;
    
    @Column(name = "ordem")
    private Integer ordem;
    
    @Column(name = "ativo")
    private Boolean ativo=true;
    
    @Column(name = "nivel")
    private Integer nivel;
        
    @Column(name = "url")
    private String url;
        
    @Column(name = "icone")
    private String icone;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuId")
//    private List<MenuUsuario> menuUsuarioList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pai")
    private List<Menu> menuList;
    
    @JoinColumn(name = "pai", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Menu pai;

    @ManyToMany
    @JoinTable(schema = "segueme", name = "menu_perfil", joinColumns = {
            @JoinColumn(name = "menu_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "perfil_id", referencedColumnName = "id")})
    private List<Perfil> perfis;
    
    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE)
    private List<MenuUsuario> listaMenuUsuario;
    
    
    public Menu() {
    }

    public Menu(Integer id) {
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

//    @XmlTransient
//    public List<MenuUsuario> getMenuUsuarioList() {
//        return menuUsuarioList;
//    }
//
//    public void setMenuUsuarioList(List<MenuUsuario> menuUsuarioList) {
//        this.menuUsuarioList = menuUsuarioList;
//    }

    @XmlTransient
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Menu getPai() {
        return pai;
    }

    public void setPai(Menu pai) {
        this.pai = pai;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public List<MenuUsuario> getListaMenuUsuario() {
        return listaMenuUsuario;
    }

    public void setListaMenuUsuario(List<MenuUsuario> listaMenuUsuario) {
        this.listaMenuUsuario = listaMenuUsuario;
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
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.seg.domain.Menu[ id=" + id + " ]";
    }
    
}
