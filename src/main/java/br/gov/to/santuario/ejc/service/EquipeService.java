package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.repository.IEquipeRepository;
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
public class EquipeService {
    @Autowired
    public IEquipeRepository repository;
    
    public EquipeService(){        
    }
    
    public void saveEquipe(Equipe objeto){
        repository.save(objeto);
    }
    
    public void saveEquipe(List<Equipe> objeto){
        repository.save(objeto);
    }
    
    public void deleteEquipe(Equipe objeto){
        repository.delete(objeto);
    }
    
    public Equipe findOneEquipe(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Equipe> findAllEquipe(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<Equipe> specificationOrder() {
        return new Specification<Equipe>() {
            @Override
            public Predicate toPredicate(Root<Equipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
