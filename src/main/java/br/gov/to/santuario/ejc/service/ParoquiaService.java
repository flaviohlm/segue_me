package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Paroquia;
import br.gov.to.santuario.ejc.repository.IParoquiaRepository;
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
public class ParoquiaService {
    @Autowired
    public IParoquiaRepository repository;

    public ParoquiaService(){        
    }
    
    public void saveParoquia(Paroquia objeto){
        repository.save(objeto);
    }
    
    public void saveParoquia(List<Paroquia> objeto){
        repository.save(objeto);
    }
    
    public void deleteParoquia(Paroquia objeto){
        repository.delete(objeto);
    }
    
    public Paroquia findOneParoquia(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Paroquia> findAllParoquia(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<Paroquia> specificationOrder() {
        return new Specification<Paroquia>() {
            @Override
            public Predicate toPredicate(Root<Paroquia> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
