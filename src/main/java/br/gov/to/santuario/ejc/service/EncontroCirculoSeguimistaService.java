package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.EncontroCirculoSeguimista;
import br.gov.to.santuario.ejc.repository.IEncontroCirculoSeguimistaRepository;
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
public class EncontroCirculoSeguimistaService {
    @Autowired
    public IEncontroCirculoSeguimistaRepository repository;
    
    public EncontroCirculoSeguimistaService(){        
    }

    public void saveEncontroCirculoSeguimista(EncontroCirculoSeguimista objeto){
        repository.save(objeto);
    }
    
    public void saveEncontroCirculoSeguimista(List<EncontroCirculoSeguimista> objeto){
        repository.save(objeto);
    }
    
    public void deleteEncontroCirculoSeguimista(EncontroCirculoSeguimista objeto){
        repository.delete(objeto);
    }
    
    public EncontroCirculoSeguimista findOneEncontroCirculoSeguimista(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<EncontroCirculoSeguimista> findAllEncontroCirculoSeguimista(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<EncontroCirculoSeguimista> specificationOrder() {
        return new Specification<EncontroCirculoSeguimista>() {
            @Override
            public Predicate toPredicate(Root<EncontroCirculoSeguimista> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
