package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.EncontroCirculo;
import br.gov.to.santuario.ejc.domain.Seguimista;
import br.gov.to.santuario.ejc.repository.ISeguimistaRepository;
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
public class SeguimistaService {
    @Autowired
    public ISeguimistaRepository repository;

    public SeguimistaService(){        
    }
    
    public void saveSeguimista(Seguimista objeto){
        repository.save(objeto);
    }
    
    public void saveSeguimista(List<Seguimista> objeto){
        repository.save(objeto);
    }
    
    public void deleteSeguimista(Seguimista objeto){
        repository.delete(objeto);
    }
    
    public Seguimista findOneSeguimista(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Seguimista> findAllSeguimista(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<Seguimista> findAllSeguimistaOrderDataNascimento(){
       return repository.findAll(where(specificationOrderDataNascimento()));
    }
    
    public List<Seguimista> findSeguimistasDisponiveis(EncontroCirculo encontroCirculo){                 
       return repository.findSeguimistasDisponiveis(encontroCirculo.getEncontro().getId(), encontroCirculo.getId());
    }
    
    
    //SPECIFICATIONS
    public Specification<Seguimista> specificationOrder() {
        return new Specification<Seguimista>() {
            @Override
            public Predicate toPredicate(Root<Seguimista> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.desc(root.get("encontro").<Integer>get("id")), cb.asc(root.get("participante").<String>get("nome")));                
                return query.getRestriction();
            }
        };
    }  
    
    public Specification<Seguimista> specificationOrderDataNascimento() {
        return new Specification<Seguimista>() {
            @Override
            public Predicate toPredicate(Root<Seguimista> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.get("participante").<Date>get("dataNascimento")));                
                return query.getRestriction();
            }
        };
    }  
}
