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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(name = "encontro_circulo", schema = "segueme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncontroCirculo.findAll", query = "SELECT e FROM EncontroCirculo e"),
    @NamedQuery(name = "EncontroCirculo.findById", query = "SELECT e FROM EncontroCirculo e WHERE e.id = :id")})
public class EncontroCirculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "circulo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Circulo circulo;
    @JoinColumn(name = "encontro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Encontro encontro;
    @JoinColumn(name = "seguidor_padrinho_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seguidor seguidorPadrinho;
    @JoinColumn(name = "seguidor_madrinha_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seguidor seguidorMadrinha;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encontroCirculo")
    private EncontroCirculoSeguimista encontroCirculoSeguimista;
    
    @JoinColumn(name = "seguidor_tio_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seguidor seguidorTio;
    @JoinColumn(name = "seguidor_tia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seguidor seguidorTia;

    @ManyToMany
    @JoinTable(schema = "segueme", name = "encontro_circulo_seguimista", joinColumns = {
            @JoinColumn(name = "encontro_circulo_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "seguimista_id", referencedColumnName = "ID")})
    private List<Seguimista> seguimistaList;
    
    public EncontroCirculo() {
    }

    public EncontroCirculo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Circulo getCirculo() {
        return circulo;
    }

    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }

    public Seguidor getSeguidorPadrinho() {
        return seguidorPadrinho;
    }

    public void setSeguidorPadrinho(Seguidor seguidorPadrinho) {
        this.seguidorPadrinho = seguidorPadrinho;
    }

    public Seguidor getSeguidorMadrinha() {
        return seguidorMadrinha;
    }

    public void setSeguidorMadrinha(Seguidor seguidorMadrinha) {
        this.seguidorMadrinha = seguidorMadrinha;
    }

    public EncontroCirculoSeguimista getEncontroCirculoSeguimista() {
        return encontroCirculoSeguimista;
    }

    public void setEncontroCirculoSeguimista(EncontroCirculoSeguimista encontroCirculoSeguimista) {
        this.encontroCirculoSeguimista = encontroCirculoSeguimista;
    }

    public List<Seguimista> getSeguimistaList() {
        return seguimistaList;
    }

    public void setSeguimistaList(List<Seguimista> seguimistaList) {
        this.seguimistaList = seguimistaList;
    }

    public Seguidor getSeguidorTio() {
        return seguidorTio;
    }

    public void setSeguidorTio(Seguidor seguidorTio) {
        this.seguidorTio = seguidorTio;
    }

    public Seguidor getSeguidorTia() {
        return seguidorTia;
    }

    public void setSeguidorTia(Seguidor seguidorTia) {
        this.seguidorTia = seguidorTia;
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
        if (!(object instanceof EncontroCirculo)) {
            return false;
        }
        EncontroCirculo other = (EncontroCirculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.santuario.ejc.domain.EncontroCirculo[ id=" + id + " ]";
    }
    
}
