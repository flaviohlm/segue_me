package br.gov.to.santuario.seg.service;

import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.repository.IParticipanteRepository;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import static org.springframework.data.jpa.domain.Specifications.where;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author flavio.madureira
 */
@Service
@Transactional(readOnly = true)
public class ParticipanteService implements UserDetailsService {

    @Autowired
    public IParticipanteRepository repository;   
    
    public ParticipanteService() {
    }
    
    public void saveParticipante(Participante usuario){
        try{
            repository.save(usuario);
        }catch(Exception e){
            System.out.println(e);            
        }
    }
    public void saveParticipante(List<Participante> usuario){
        try{
            repository.save(usuario);
        }catch(Exception e){
            System.out.println(e);            
        }
    }
    
    public void deleteParticipante(Participante usuario){
        repository.delete(usuario);
    }        
    
    public void updateParticipante(Participante usuario){
        repository.saveAndFlush(usuario);
    }
    
    public Participante findOneParticipante(Integer codigo){
        return repository.findOne(codigo);
    }
    
    public List<Participante> findAllParticipantes() {
        try{
            return repository.findAll(where(specificationAll()));
        }catch(Exception e){
            System.out.println(e);            
        }
        return null;
    }
    
    public Participante findByNomeNascimento(Participante participante){
        List<Participante>lista = repository.findAll(where(specificationNomeNascimento(participante.getNome(), participante.getDataNascimento())));
        if(lista.isEmpty()){
            return null;
        }
        return lista.get(0);
    }
    
    public List<Participante> findByNome(String nome){
        return repository.findAll(where(specificationNome(nome)));
    }
    
    public Specification<Participante> specificationAll() {
        return new Specification<Participante>() {
            @Override
            public Predicate toPredicate(Root<Participante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                return query.getRestriction();
            }
        };
    }

    public Specification<Participante> specificationNomeNascimento(final String nome, final Date nascimento) {
        return new Specification<Participante>() {
            @Override
            public Predicate toPredicate(Root<Participante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                
                Predicate p1 = cb.like(root.<String>get("nome"), nome);
                Predicate p2 = cb.equal(root.<Date>get("dataNascimento"), nascimento);
                
                return cb.and(p1, p2);
            }
        };
    }
    
    public Specification<Participante> specificationNome(final String nome) {
        return new Specification<Participante>() {
            @Override
            public Predicate toPredicate(Root<Participante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                
                Predicate p1 = cb.like(cb.lower(root.<String>get("nome")), "%"+nome.toLowerCase()+"%");
                
                return cb.and(p1);
            }
        };
    }
     
    
    @Transactional(timeout = 10)
    public Participante findByCpf(String cpf){        
        try{
            Participante c = repository.findByCpf(cpf);            
            if(c==null){
                System.out.println("vazio");
                System.out.println(c);
            }
            return c;
        }catch(Exception e){
            System.out.println("ERRO: "+e.getLocalizedMessage());
            return null;
        }
    }        
    
    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        return findByCpf(str);
    }
    
    
    public List<Participante> findAllParticipantes2() {
        try{
            return repository.findAll(where(specificationAll2()));
        }catch(Exception e){
            System.out.println(e);            
        }
        return null;
    }
    
    public Specification<Participante> specificationAll2() {
        return new Specification<Participante>() {
            @Override
            public Predicate toPredicate(Root<Participante> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                
                query.orderBy(cb.asc(root.<Integer>get("id")));                
                
                Predicate p1 = cb.greaterThan(root.<Integer>get("id"), 540);
                
                return cb.and(p1);
            }
        };
    }

}
