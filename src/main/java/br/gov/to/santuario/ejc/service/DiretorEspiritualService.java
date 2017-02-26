package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.DiretorEspiritual;
import br.gov.to.santuario.ejc.repository.IDiretorEspiritualRepository;
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
public class DiretorEspiritualService {
    @Autowired
    public IDiretorEspiritualRepository repository;

    public DiretorEspiritualService(){        
    }
    
    public void saveDiretorEspiritual(DiretorEspiritual objeto){
        repository.save(objeto);
    }
    
    public void saveDiretorEspiritual(List<DiretorEspiritual> objeto){
        repository.save(objeto);
    }
    
    public void deleteDiretorEspiritual(DiretorEspiritual objeto){
        repository.delete(objeto);
    }
    
    public DiretorEspiritual findOneDiretorEspiritual(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<DiretorEspiritual> findAllDiretorEspiritual(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public DiretorEspiritual findByNome(String nome){
        List<DiretorEspiritual>lista = repository.findAll(where(specificationNome(nome)));
        if(lista.isEmpty()){
            return null;
        }
        return lista.get(0);
    }
    
    public DiretorEspiritual findByParticipante(Integer id){
        List<DiretorEspiritual>lista = repository.findAll(where(specificationidParticipante(id)));
        if(lista.isEmpty()){
            return null;
        }
        return lista.get(0);
    }
    
    //SPECIFICATIONS
    public Specification<DiretorEspiritual> specificationOrder() {
        return new Specification<DiretorEspiritual>() {
            @Override
            public Predicate toPredicate(Root<DiretorEspiritual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.get("participante").<String>get("nome")));                
                return query.getRestriction();
            }
        };
    }
    
    public Specification<DiretorEspiritual> specificationNome(final String nome) {
        return new Specification<DiretorEspiritual>() {
            @Override
            public Predicate toPredicate(Root<DiretorEspiritual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                
                Predicate p = cb.like(root.get("participante").<String>get("nome"), nome);
                
                return query.getRestriction();
            }
        };
    }

    public Specification<DiretorEspiritual> specificationidParticipante(final Integer id) {
        return new Specification<DiretorEspiritual>() {
            @Override
            public Predicate toPredicate(Root<DiretorEspiritual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                
                Predicate p = cb.equal(root.get("participante").<Integer>get("id"), id);
                
                return cb.and(p);
            }
        };
    }
}
