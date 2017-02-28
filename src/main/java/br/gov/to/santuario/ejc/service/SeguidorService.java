package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.EncontroCirculo;
import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.repository.ISeguidorRepository;
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
public class SeguidorService {
    @Autowired
    public ISeguidorRepository repository;

    public SeguidorService(){        
    }
    
    public void saveSeguidor(Seguidor objeto){
        repository.save(objeto);
    }
    
    public void saveSeguidor(List<Seguidor> objeto){
        repository.save(objeto);
    }
    
    public void deleteSeguidor(Seguidor objeto){
        repository.delete(objeto);
    }
    
    public Seguidor findOneSeguidor(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Seguidor> findAllSeguidor(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<Seguidor> findSeguidoresDisponiveis(EncontroEquipe encontroEquipe){        
       return repository.findSeguidoresDisponiveis(encontroEquipe.getId(), encontroEquipe.getEncontro().getId());
    }
    
    //Retorna os seguidores que estao na equipe de circulos
    public List<Seguidor> findSeguidoresPadrinhosDisponiveis(EncontroCirculo encontroCirculo, String sexo, Boolean tio){           
       return repository.findSeguidoresPadrinhosDisponiveis(encontroCirculo.getEncontro().getId(), sexo, tio);
    }
    
    public List<Seguidor> findSeguidoresEquipeDirigente(Integer id){           
       return repository.findSeguidoresEquipeDirigente(id);
    }
    
     public List<Seguidor> findSeguidoresConselho(Integer id){           
       return repository.findSeguidoresConselho(id);
    }
    
    public Seguidor findByParticipante(Integer id){
        List<Seguidor>lista = repository.findAll(where(specificationidParticipante(id)));
        if(lista.isEmpty()){
            return null;
        }
        return lista.get(0);
    }
    
    //SPECIFICATIONS
    public Specification<Seguidor> specificationOrder() {
        return new Specification<Seguidor>() {
            @Override
            public Predicate toPredicate(Root<Seguidor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.get("participante").<String>get("nome")));                
                return query.getRestriction();
            }
        };
    }
    
    public Specification<Seguidor> specificationidParticipante(final Integer id) {
        return new Specification<Seguidor>() {
            @Override
            public Predicate toPredicate(Root<Seguidor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                
                Predicate p = cb.equal(root.get("participante").<Integer>get("id"), id);
                
                return cb.and(p);
            }
        };
    }
    
}
