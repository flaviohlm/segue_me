package br.gov.to.santuario.ejc.service;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
import br.gov.to.santuario.ejc.repository.IEncontroEquipeIntegranteRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<EncontroEquipeIntegrante> findAll(PageRequest request, String string) {       
        //sempre mostrar o último inserido se for null 
        if (request.getSort() != null) {
            request.getSort().and(new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        } else {  
            List<Sort.Order>listOrders = new ArrayList<>();
            listOrders.add(new Sort.Order(Sort.Direction.ASC, "encontroEquipe.equipe.ordemQuadrante"));
            listOrders.add(new Sort.Order(Sort.Direction.ASC, "encontroEquipe.equipe.descricao"));
            listOrders.add(new Sort.Order(Sort.Direction.ASC, "seguidor.participante.nome"));            
            PageRequest tmp = new PageRequest(request.getPageNumber(), request.getPageSize(), new Sort(listOrders));
            request = tmp;
        }       
        
         return repository.findAll(where(specificationGenerico(string, "O", "seguidor", "participante", "nome")).
                or(specificationGenerico(string, "O", "seguidor", "participante", "apelido")).
                or(specificationGenerico(string, "O", "seguidor", "participante", "telefoneResidencial")).
                or(specificationGenerico(string, "O", "seguidor", "participante", "endereco")).
                or(specificationGenerico(string, "O", "seguidor", "participante", "telefoneCelular")).
                or(specificationGenerico(string, "O", "encontroEquipe", "equipe", "descricao")), request);
    }
    
    public Specification<EncontroEquipeIntegrante> specificationGenerico(final Object object, final String tipo,
            final String fieldCampo, final String fieldFilho1, final String fieldFilho2) {

        return new Specification<EncontroEquipeIntegrante>() {
            @Override
            public Predicate toPredicate(Root<EncontroEquipeIntegrante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                if (object != null) {
                    switch (tipo) {
                        case "I":
                            return cb.like(cb.lower(root.<String>get(fieldCampo)), Integer.getInteger(object.toString()) > 0 && Integer.getInteger(object.toString()) != null ? "%" + object + "%" : null);
                        case "S":
                            return cb.like(cb.lower(root.<String>get(fieldCampo)), object.toString() != null ? getLikePattern(object.toString()) : null);
                        case "P":
                            return cb.like(cb.lower(root.get(fieldCampo).<String>get(fieldFilho1)), getLikePattern(object.toString()));
                        case "O":
                            return cb.like(cb.lower(root.get(fieldCampo).get(fieldFilho1).<String>get(fieldFilho2)), getLikePattern(object.toString()));
                        default:
                            return null;
                    }
                }
                return null;
            }

            private String getLikePattern(final String searchTerm) {
                StringBuilder pattern = new StringBuilder();
                pattern.append("%");
                pattern.append(searchTerm.toLowerCase());
                pattern.append("%");
                return pattern.toString();
            }

        };
    }
}
