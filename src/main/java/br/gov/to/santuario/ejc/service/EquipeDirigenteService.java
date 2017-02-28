package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.EquipeDirigente;
import br.gov.to.santuario.ejc.repository.IEquipeDirigenteRepository;
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
public class EquipeDirigenteService {
    @Autowired
    public IEquipeDirigenteRepository repository;
    
    public EquipeDirigenteService(){        
    }
    
    public void saveEquipeDirigente(EquipeDirigente objeto){
        repository.save(objeto);
    }
    
    public void saveEquipeDirigente(List<EquipeDirigente> objeto){
        repository.save(objeto);
    }
    
    public void deleteEquipeDirigente(EquipeDirigente objeto){
        repository.delete(objeto);
    }
    
    public EquipeDirigente findOneEquipeDirigente(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<EquipeDirigente> findAllEquipeDirigente(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<EquipeDirigente> findAllEquipeDirigenteAtiva(Integer idParoquia){
       return repository.findAll(where(specificationAtivos(idParoquia)));
    }
    
    //SPECIFICATIONS
    public Specification<EquipeDirigente> specificationOrder() {
        return new Specification<EquipeDirigente>() {
            @Override
            public Predicate toPredicate(Root<EquipeDirigente> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.desc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
    public Specification<EquipeDirigente> specificationAtivos(final Integer idParoquia) {
        return new Specification<EquipeDirigente>() {
            @Override
            public Predicate toPredicate(Root<EquipeDirigente> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.desc(root.<Integer>get("id")));  
                
                Predicate p1 = cb.equal(root.get("paroquia").<Integer>get("id"), idParoquia);
                Predicate p2 = cb.isNull(root.<Date>get("dataFim"));
                
                return cb.and(p1, p2);
            }
        };
    }
    
}
