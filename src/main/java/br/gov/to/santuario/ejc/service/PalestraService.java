package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Palestra;
import br.gov.to.santuario.ejc.repository.IPalestraRepository;
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
public class PalestraService {
    @Autowired
    public IPalestraRepository repository;
    
    public PalestraService(){        
    }
    
    public void savePalestras(Palestra objeto){
        repository.save(objeto);
    }
    
    public void savePalestras(List<Palestra> objeto){
        repository.save(objeto);
    }
    
    public void deletePalestras(Palestra objeto){
        repository.delete(objeto);
    }
    
    public Palestra findOnePalestras(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Palestra> findAllPalestras(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<Palestra> specificationOrder() {
        return new Specification<Palestra>() {
            @Override
            public Predicate toPredicate(Root<Palestra> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
