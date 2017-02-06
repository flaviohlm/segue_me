package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Circulo;
import br.gov.to.santuario.ejc.repository.ICirculoRepository;
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
public class CirculoService {
    @Autowired
    public ICirculoRepository repository;
    
    public CirculoService(){        
    }
    
    public void saveCirculo(Circulo objeto){
        repository.save(objeto);
    }
    
    public void saveCirculo(List<Circulo> objeto){
        repository.save(objeto);
    }
    
    public void deleteCirculo(Circulo objeto){
        repository.delete(objeto);
    }
    
    public Circulo findOneCirculo(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Circulo> findAllCirculo(){
       return repository.findAll(where(specificationOrder()));
    }
        
    public List<Circulo> findAllCirculoParaPaisCirculo(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<Circulo> specificationOrder() {
        return new Specification<Circulo>() {
            @Override
            public Predicate toPredicate(Root<Circulo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
}