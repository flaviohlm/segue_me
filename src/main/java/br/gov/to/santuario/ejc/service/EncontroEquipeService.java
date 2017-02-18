package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.repository.IEncontroEquipeRepository;
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
public class EncontroEquipeService {
    @Autowired
    public IEncontroEquipeRepository repository;
    
    public EncontroEquipeService(){        
    }
    
    public void saveEncontroEquipe(EncontroEquipe objeto){
        repository.save(objeto);
    }
    
    public void saveEncontroEquipe(List<EncontroEquipe> objeto){
        repository.save(objeto);
    }
    
    public void deleteEncontroEquipe(EncontroEquipe objeto){
        repository.delete(objeto);
    }
    
    public EncontroEquipe findOneEncontroEquipe(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<EncontroEquipe> findAllEncontroEquipe(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<EncontroEquipe> findAllByEncontro(Encontro encontro){
       return repository.findAll(where(specificationEncontro(encontro)));
    }
    
    //SPECIFICATIONS
    public Specification<EncontroEquipe> specificationOrder() {
        return new Specification<EncontroEquipe>() {
            @Override
            public Predicate toPredicate(Root<EncontroEquipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
    public Specification<EncontroEquipe> specificationEncontro(final Encontro encontro) {
        return new Specification<EncontroEquipe>() {
            @Override
            public Predicate toPredicate(Root<EncontroEquipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.get("equipe").<String>get("descricao")));                
                
                Predicate p1 = cb.equal(root.get("encontro"), encontro);
                
                return cb.and(p1);
            }
        };
    }
    
}
