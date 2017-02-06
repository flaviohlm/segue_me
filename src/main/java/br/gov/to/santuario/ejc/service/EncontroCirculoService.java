package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.EncontroCirculo;
import br.gov.to.santuario.ejc.repository.IEncontroCirculoRepository;
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
public class EncontroCirculoService {
    @Autowired
    public IEncontroCirculoRepository repository;
    
    public EncontroCirculoService(){        
    }
    
    public void saveEncontroCirculo(EncontroCirculo objeto){
        repository.save(objeto);
    }
    
    public void saveEncontroCirculo(List<EncontroCirculo> objeto){
        repository.save(objeto);
    }
    
    public void deleteEncontroCirculo(EncontroCirculo objeto){
        repository.delete(objeto);
    }
    
    public EncontroCirculo findOneEncontroCirculo(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<EncontroCirculo> findAllEncontroCirculo(){
       return repository.findAll(where(specificationOrder()));
    }
        
    public List<EncontroCirculo> findAllEncontroCirculoParaPaisEncontroCirculo(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<EncontroCirculo> findAllEncontroCirculoByEncontro(Integer id){
       return repository.findAll(where(specificationEncontro(id)));
    }
    
    //SPECIFICATIONS
    public Specification<EncontroCirculo> specificationOrder() {
        return new Specification<EncontroCirculo>() {
            @Override
            public Predicate toPredicate(Root<EncontroCirculo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
    public Specification<EncontroCirculo> specificationEncontro(final Integer idEncontro) {
        return new Specification<EncontroCirculo>() {
            @Override
            public Predicate toPredicate(Root<EncontroCirculo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.get("circulo").<String>get("cor")));  
                
                Predicate pEncontro = cb.equal(root.get("encontro").<Integer>get("id"), idEncontro);                
                                
                return cb.and(pEncontro);
                    
            }
        };
    }
}