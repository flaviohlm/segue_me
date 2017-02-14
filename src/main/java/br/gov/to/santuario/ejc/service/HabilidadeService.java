package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.repository.IHabilidadeRepository;
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
public class HabilidadeService {
    @Autowired
    public IHabilidadeRepository repository;
    
    public HabilidadeService(){        
    }
    
    public void saveHabilidade(Habilidade objeto){
        repository.save(objeto);
    }
    
    public void saveHabilidade(List<Habilidade> objeto){
        repository.save(objeto);
    }
    
    public void deleteHabilidade(Habilidade objeto){
        repository.delete(objeto);
    }
    
    public Habilidade findOneHabilidade(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Habilidade> findAllHabilidade(){
       return repository.findAll(where(specificationOrder()));
    }
    
    
    //SPECIFICATIONS
    public Specification<Habilidade> specificationOrder() {
        return new Specification<Habilidade>() {
            @Override
            public Predicate toPredicate(Root<Habilidade> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<String>get("descricao")));                
                return query.getRestriction();
            }
        };
    }
    
}
