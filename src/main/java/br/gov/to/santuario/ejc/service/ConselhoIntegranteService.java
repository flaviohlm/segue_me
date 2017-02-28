package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.ConselhoIntegrante;
import br.gov.to.santuario.ejc.repository.IConselhoIntegranteRepository;
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
public class ConselhoIntegranteService {
    @Autowired
    public IConselhoIntegranteRepository repository;
    
    public ConselhoIntegranteService(){        
    }
    
    public void saveConselhoIntegrante(ConselhoIntegrante objeto){
        repository.save(objeto);
    }
    
    public void saveConselhoIntegrante(List<ConselhoIntegrante> objeto){
        repository.save(objeto);
    }
    
    public void deleteConselhoIntegrante(ConselhoIntegrante objeto){
        repository.delete(objeto);
    }
    
    public ConselhoIntegrante findOneConselhoIntegrante(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<ConselhoIntegrante> findAllConselhoIntegrante(){
       return repository.findAll(where(specificationOrder()));
    }
    
    //SPECIFICATIONS
    public Specification<ConselhoIntegrante> specificationOrder() {
        return new Specification<ConselhoIntegrante>() {
            @Override
            public Predicate toPredicate(Root<ConselhoIntegrante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.desc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
