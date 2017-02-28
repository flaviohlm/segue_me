package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.EquipeDirigente;
import br.gov.to.santuario.ejc.domain.EquipeDirigenteIntegrante;
import br.gov.to.santuario.ejc.repository.IEquipeDirigenteIntegranteRepository;
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
public class EquipeDirigenteIntegranteService {
    @Autowired
    public IEquipeDirigenteIntegranteRepository repository;
    
    public EquipeDirigenteIntegranteService(){        
    }

    public void saveEquipeDirigenteIntegrante(EquipeDirigenteIntegrante objeto){
        repository.save(objeto);
    }
    
    public void saveEquipeDirigenteIntegrante(List<EquipeDirigenteIntegrante> objeto){
        repository.save(objeto);
    }
    
    public void deleteEquipeDirigenteIntegrante(EquipeDirigenteIntegrante objeto){
        repository.delete(objeto);
    }
    
    public EquipeDirigenteIntegrante findOneEquipeDirigenteIntegrante(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<EquipeDirigenteIntegrante> findAllEquipeDirigenteIntegrante(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<EquipeDirigenteIntegrante> findSeguidoresDaEquipe(EquipeDirigente equipeDirigente){        
       return repository.findSeguidoresDaEquipe(equipeDirigente.getId());
    }
    
    //SPECIFICATIONS
    public Specification<EquipeDirigenteIntegrante> specificationOrder() {
        return new Specification<EquipeDirigenteIntegrante>() {
            @Override
            public Predicate toPredicate(Root<EquipeDirigenteIntegrante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
}
