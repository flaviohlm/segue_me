package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Funcao;
import br.gov.to.santuario.ejc.repository.IFuncaoRepository;
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
public class FuncaoService {
    @Autowired
    public IFuncaoRepository repository;
    
    public FuncaoService(){        
    }
    
    public void saveFuncao(Funcao objeto){
        repository.save(objeto);
    }
    
    public void saveFuncao(List<Funcao> objeto){
        repository.save(objeto);
    }
    
    public void deleteFuncao(Funcao objeto){
        repository.delete(objeto);
    }
    
    public Funcao findOneFuncao(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Funcao> findAllFuncao(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<Funcao> findAllFuncaoByTabela(String tabela){
       return repository.findAll(where(specificationByTabela(tabela)));
    }
    
    //SPECIFICATIONS
    public Specification<Funcao> specificationOrder() {
        return new Specification<Funcao>() {
            @Override
            public Predicate toPredicate(Root<Funcao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<String>get("descricao")));                
                return query.getRestriction();
            }
        };
    }
   
    public Specification<Funcao> specificationByTabela(final String tabela) {
        return new Specification<Funcao>() {
            @Override
            public Predicate toPredicate(Root<Funcao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<String>get("descricao")));


                Predicate p1 = cb.equal(root.<String>get("tabela"), tabela);
                
                return cb.and(p1);
            }
        };
    }
}
