package br.gov.to.santuario.seg.service;

import br.gov.to.santuario.seg.domain.PerfilUsuario;
import br.gov.to.santuario.seg.repository.IPerfilUsuarioRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import static org.springframework.data.jpa.domain.Specifications.where;
import org.springframework.stereotype.Service;

/**
 *
 * @author flavio.madureira
 */
@Service
public class PerfilUsuarioService {
    
    @Autowired
    private IPerfilUsuarioRepository perfilUsuarioRepository;    
    
    public PerfilUsuarioService() {
    }
    
    public void savePerfilUsuario(PerfilUsuario menuUsuario){
        perfilUsuarioRepository.save(menuUsuario);
    }
    
    public void deletePerfilUsuario(PerfilUsuario menuUsuario){
        perfilUsuarioRepository.delete(menuUsuario);
    }
    
    public List<PerfilUsuario> findAllPerfilUsuario(){
        return perfilUsuarioRepository.findAll();
    } 
    
    public PerfilUsuario findOnePerfilUsuario(Integer codigo){
        return perfilUsuarioRepository.findOne(codigo);
    }
    
    public List<PerfilUsuario> findByCpf(String cpf){
        return perfilUsuarioRepository.findByCpf(cpf);
    }
    
    public List<PerfilUsuario> findATivosByCpf(String cpf){
        return perfilUsuarioRepository.findAtivosByCpf(cpf);
    }
    
    public List<PerfilUsuario> findByPerfilUsuario(Integer idPerfil, Integer idUsuario){
        return perfilUsuarioRepository.findAll(where(specificationPerfilUsuario(idPerfil, idUsuario)));
    } 
    
    public Specification<PerfilUsuario> specification(final Integer idUnidade, final String descricaoJornada) {
        return new Specification<PerfilUsuario>() {
            @Override
            public Predicate toPredicate(Root<PerfilUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.get("usuario").<String>get("nome")));  
                Predicate perfilAdministrador = cb.notLike(root.get("perfil").<String>get("nome"), "Administrador");                
                                
                return cb.and(perfilAdministrador);
                    
            }
        };
    }
    
    public Specification<PerfilUsuario> specificationPerfilUsuario(final Integer idPerfil, final Integer idUsuario) {
        return new Specification<PerfilUsuario>() {
            @Override
            public Predicate toPredicate(Root<PerfilUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                //query.orderBy(cb.asc(root.get("usuario").<String>get("nome")));  
                
                Predicate predicatePerfil = cb.equal(root.get("perfil").<Integer>get("id"), idPerfil);
                Predicate predicateUsuario = cb.equal(root.get("usuario").<Integer>get("id"), idUsuario);
                                
                return cb.and(predicatePerfil, predicateUsuario);
                    
            }
        };
    }
}
