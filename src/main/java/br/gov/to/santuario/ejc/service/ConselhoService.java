package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Conselho;
import br.gov.to.santuario.ejc.repository.IConselhoRepository;
import java.util.Date;
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
public class ConselhoService {
    @Autowired
    public IConselhoRepository repository;
    
    public ConselhoService(){        
    }
    
    public void saveConselho(Conselho objeto){
        repository.save(objeto);
    }
    
    public void saveConselho(List<Conselho> objeto){
        repository.save(objeto);
    }
    
    public void deleteConselho(Conselho objeto){
        repository.delete(objeto);
    }
    
    public Conselho findOneConselho(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Conselho> findAllConselho(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<Conselho> findAllConselhoAtiva(){
       return repository.findAll(where(specificationAtivos()));
    }
    
    //SPECIFICATIONS
    public Specification<Conselho> specificationOrder() {
        return new Specification<Conselho>() {
            @Override
            public Predicate toPredicate(Root<Conselho> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.desc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
    public Specification<Conselho> specificationAtivos() {
        return new Specification<Conselho>() {
            @Override
            public Predicate toPredicate(Root<Conselho> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.desc(root.<Integer>get("id")));  
                
                Predicate p2 = cb.isNull(root.<Date>get("dataFim"));
                
                return cb.and(p2);
            }
        };
    }
    
}
