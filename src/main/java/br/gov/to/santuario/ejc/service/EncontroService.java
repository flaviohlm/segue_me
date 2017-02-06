package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.repository.IEncontroRepository;
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
public class EncontroService {
    @Autowired
    public IEncontroRepository repository;

    public EncontroService(){        
    }
    
    public void saveEncontro(Encontro objeto){
        repository.save(objeto);
    }
    
    public void saveEncontro(List<Encontro> objeto){
        repository.save(objeto);
    }
    
    public void deleteEncontro(Encontro objeto){
        repository.delete(objeto);
    }
    
    public Encontro findOneEncontro(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Encontro> findAllEncontro(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<Encontro> specificationOrder() {
        return new Specification<Encontro>() {
            @Override
            public Predicate toPredicate(Root<Encontro> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.desc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
