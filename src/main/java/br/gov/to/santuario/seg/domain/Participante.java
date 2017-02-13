package br.gov.to.santuario.seg.domain;

import br.gov.to.santuario.ejc.domain.DiretorEspiritual;
import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.domain.Palestrante;
import br.gov.to.santuario.ejc.domain.ParticipanteHabilidade;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.domain.Seguimista;
import br.gov.to.santuario.seg.util.ConvertePasswordParaMD5;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author flavio.madureira
 */
@Entity
@Table(catalog = "postgres", schema = "segueme", name="participante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participante.findAll", query = "SELECT u FROM Participante u"),
    @NamedQuery(name = "Participante.findById", query = "SELECT u FROM Participante u WHERE u.id = :id"),
    @NamedQuery(name = "Participante.findByCpf", query = "SELECT u FROM Participante u WHERE u.cpf = :cpf"),
    @NamedQuery(name = "Participante.findByPassword", query = "SELECT u FROM Participante u WHERE u.password = :password")})
public class Participante implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
        
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "apelido")
    private String apelido;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    
    @Size(max = 250)
    @Column(name = "email")
    private String email;
    
    @Column(name = "sexo")
    private String sexo = "F";
    
    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name = "bairro")
    private String bairro;
    
    @Column(name = "telefone_residencial")
    private String telefoneResidencial;
    
    @Column(name = "telefone_celular")
    private String telefoneCelular;
    
    @Column(name = "nome_pai")
    private String nomePai;
    
    @Column(name = "nome_mae")
    private String nomeMae;
        
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante")
    private List<DiretorEspiritual> diretorEspiritualList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante")
    private List<ParticipanteHabilidade> participanteHabilidadeList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante")
    private List<Palestrante> palestranteList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante")
    private List<Seguimista> seguimistaList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante")
    private List<Seguidor> seguidorList;
    
    @Column(name = "cpf")
    private String cpf;    
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "redefinir_senha")
    private Boolean redefinirSenha = true;  
    
    @Column(name = "ativo")
    private boolean ativo = true;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante")
    private List<PerfilUsuario> perfilParticipanteList;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "perfil_usuario", joinColumns = {
            @JoinColumn(name = "participante_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "perfil_id", referencedColumnName = "ID")})
    private List<Perfil> listaPerfis;
    
    @ManyToMany
    @JoinTable(schema = "segueme", name = "participante_habilidade", joinColumns = {
            @JoinColumn(name = "participante_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "habilidade_id", referencedColumnName = "ID")})
    private List<Habilidade> habilidadeList;
    
    @Transient
    private Integer idade;
    
    @Transient
    private String habilidades;
    
    public Participante() {          
    }

    public Participante(Integer id) {
        this.id = id;
    }

    public Participante(Integer id, Date dtini, String cpf) {
        this.id = id;        
        this.cpf = cpf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {        
        this.cpf = cpf.replaceAll("\\.","").replace("-","");
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = ConvertePasswordParaMD5.convertePasswordParaMD5(password);
    }

    public Boolean getRedefinirSenha() {
        return redefinirSenha;
    }

    public void setRedefinirSenha(Boolean redefinirSenha) {
        this.redefinirSenha = redefinirSenha;
    }

    @XmlTransient
    public List<PerfilUsuario> getPerfilUsuarioList() {
        return perfilParticipanteList;
    }

    public void setPerfilUsuarioList(List<PerfilUsuario> perfilParticipanteList) {
        this.perfilParticipanteList = perfilParticipanteList;
    }

    public List<Perfil> getListaPerfis() {
        return listaPerfis;
    }

    public void setListaPerfis(List<Perfil> listaPerfis) {
        this.listaPerfis = listaPerfis;
    }

    public Integer getIdade() {
        if(getDataNascimento() != null){
            Calendar dataNascimentoAux = Calendar.getInstance();  
            dataNascimentoAux.setTime(getDataNascimento()); 
            Calendar hoje = Calendar.getInstance();  

            idade = hoje.get(Calendar.YEAR) - dataNascimentoAux.get(Calendar.YEAR); 

            if (hoje.get(Calendar.MONTH) < dataNascimentoAux.get(Calendar.MONTH)) {
              idade--;  
            } 
            else 
            { 
                if (hoje.get(Calendar.MONTH) == dataNascimentoAux.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimentoAux.get(Calendar.DAY_OF_MONTH)) {
                    idade--; 
                }
            }
        }
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getHabilidades() {
        if(!habilidadeList.isEmpty()){            
            habilidades = habilidadeList.get(0).getDescricao();            
            for(int i=1; i < habilidadeList.size(); i++){                
                habilidades += ", "+habilidadeList.get(i).getDescricao();              
            }
        }
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
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
        if (!(object instanceof Participante)) {
            return false;
        }
        Participante other = (Participante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.to.secad.seg.domain.Participante[ id=" + id + " ]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        
//        for(Perfil permissao : listaPerfis){
//            result.add(new GrantedAuthorityImpl(permissao.getNome()));
//        }
        
        return result;
    }

    @Override
    public String getUsername() {
        return getCpf();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    @XmlTransient
    public List<DiretorEspiritual> getDiretorEspiritualList() {
        return diretorEspiritualList;
    }

    public void setDiretorEspiritualList(List<DiretorEspiritual> diretorEspiritualList) {
        this.diretorEspiritualList = diretorEspiritualList;
    }

    @XmlTransient
    public List<ParticipanteHabilidade> getParticipanteHabilidadeList() {
        return participanteHabilidadeList;
    }

    public void setParticipanteHabilidadeList(List<ParticipanteHabilidade> participanteHabilidadeList) {
        this.participanteHabilidadeList = participanteHabilidadeList;
    }

    @XmlTransient
    public List<Palestrante> getPalestranteList() {
        return palestranteList;
    }

    public void setPalestranteList(List<Palestrante> palestranteList) {
        this.palestranteList = palestranteList;
    }

    @XmlTransient
    public List<Seguimista> getSeguimistaList() {
        return seguimistaList;
    }

    public void setSeguimistaList(List<Seguimista> seguimistaList) {
        this.seguimistaList = seguimistaList;
    }

    @XmlTransient
    public List<Seguidor> getSeguidorList() {
        return seguidorList;
    }

    public void setSeguidorList(List<Seguidor> seguidorList) {
        this.seguidorList = seguidorList;
    }

    public List<PerfilUsuario> getPerfilParticipanteList() {
        return perfilParticipanteList;
    }

    public void setPerfilParticipanteList(List<PerfilUsuario> perfilParticipanteList) {
        this.perfilParticipanteList = perfilParticipanteList;
    }

    public List<Habilidade> getHabilidadeList() {
        return habilidadeList;
    }

    public void setHabilidadeList(List<Habilidade> habilidadeList) {
        this.habilidadeList = habilidadeList;
    }
    
}
