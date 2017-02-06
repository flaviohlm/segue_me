package br.gov.to.santuario.seg.service;

import br.gov.to.santuario.seg.domain.Perfil;
import br.gov.to.santuario.seg.repository.IPerfilRepository;
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
 * @author ana.dias
 */
@Service
public class PerfilService {
    
    @Autowired
    public IPerfilRepository perfilRepository;
    
    public PerfilService(){
        
    }
    
    public void savePerfil(Perfil perfil){
        perfilRepository.save(perfil);
    }
    
    public void deletePerfil(Perfil perfil){
        perfilRepository.delete(perfil);
    }
    
    public List<Perfil> findAllPerfil(){
       return perfilRepository.findAll(where(specificationOrderNivel()));
    }
    
    public Perfil findOnePerfil(Integer codigo){
        return perfilRepository.findOne(codigo);
    }    
    
    public Specification<Perfil> specificationOrderNivel() {
        return new Specification<Perfil>() {
            @Override
            public Predicate toPredicate(Root<Perfil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("nivel")));                
                return query.getRestriction();
            }
        };
    }
}
