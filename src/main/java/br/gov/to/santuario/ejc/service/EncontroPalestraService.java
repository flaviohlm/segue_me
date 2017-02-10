package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.EncontroPalestra;
import br.gov.to.santuario.ejc.repository.IEncontroPalestraRepository;
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
public class EncontroPalestraService {
    @Autowired
    public IEncontroPalestraRepository repository;
    
    public EncontroPalestraService(){        
    }
    
    public void saveEncontroPalestra(EncontroPalestra objeto){
        repository.save(objeto);
    }
    
    public void saveEncontroPalestra(List<EncontroPalestra> objeto){
        repository.save(objeto);
    }
    
    public void deleteEncontroPalestra(EncontroPalestra objeto){
        repository.delete(objeto);
    }
    
    public EncontroPalestra findOneEncontroPalestra(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<EncontroPalestra> findAllEncontroPalestra(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<EncontroPalestra> specificationOrder() {
        return new Specification<EncontroPalestra>() {
            @Override
            public Predicate toPredicate(Root<EncontroPalestra> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
