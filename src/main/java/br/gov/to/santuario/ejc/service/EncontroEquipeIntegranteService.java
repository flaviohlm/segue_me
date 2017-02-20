package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
import br.gov.to.santuario.ejc.repository.IEncontroEquipeIntegranteRepository;
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
public class EncontroEquipeIntegranteService {
    @Autowired
    public IEncontroEquipeIntegranteRepository repository;
    
    public EncontroEquipeIntegranteService(){        
    }
    
    public void saveEncontroEquipeIntegrante(EncontroEquipeIntegrante objeto){
        repository.save(objeto);
    }
    
    public void saveEncontroEquipeIntegrante(List<EncontroEquipeIntegrante> objeto){
        repository.save(objeto);
    }
    
    public void deleteEncontroEquipeIntegrante(EncontroEquipeIntegrante objeto){
        repository.delete(objeto);
    }
    
    public EncontroEquipeIntegrante findOneEncontroEquipeIntegrante(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<EncontroEquipeIntegrante> findAllEncontroEquipeIntegrante(){
       return repository.findAll(where(specificationOrder()));
    }
    
    public List<EncontroEquipeIntegrante> findSeguidoresDaEquipe(EncontroEquipe encontroEquipe){        
       return repository.findSeguidoresDaEquipe(encontroEquipe.getId());
    }
    
    public List<EncontroEquipeIntegrante> findAllByEncontro(Encontro encontro){
       return repository.findAll(where(specificationOrderByEncontro(encontro)));
    }
    
    //SPECIFICATIONS
    public Specification<EncontroEquipeIntegrante> specificationOrder() {
        return new Specification<EncontroEquipeIntegrante>() {
            @Override
            public Predicate toPredicate(Root<EncontroEquipeIntegrante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }
    
    public Specification<EncontroEquipeIntegrante> specificationOrderByEncontro(final Encontro encontro) {
        return new Specification<EncontroEquipeIntegrante>() {
            @Override
            public Predicate toPredicate(Root<EncontroEquipeIntegrante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.get("encontroEquipe").get("equipe").<Integer>get("ordemQuadrante")), cb.asc(root.get("seguidor").get("participante").<String>get("nome")));     
                
                Predicate p1 = cb.equal(root.get("encontroEquipe").get("encontro"), encontro);
                
                return cb.and(p1);
            }
        };
    }
}
