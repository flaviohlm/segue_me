package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Palestrante;
import br.gov.to.santuario.ejc.repository.IPalestranteRepository;
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
public class PalestranteService {
    @Autowired
    public IPalestranteRepository repository;

    public PalestranteService(){        
    }
    
    public void savePalestrante(Palestrante objeto){
        repository.save(objeto);
    }
    
    public void savePalestrante(List<Palestrante> objeto){
        repository.save(objeto);
    }
    
    public void deletePalestrante(Palestrante objeto){
        repository.delete(objeto);
    }
    
    public Palestrante findOnePalestrante(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Palestrante> findAllPalestrante(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<Palestrante> specificationOrder() {
        return new Specification<Palestrante>() {
            @Override
            public Predicate toPredicate(Root<Palestrante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
