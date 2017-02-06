package br.gov.to.santuario.seg.service;

import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.repository.IParticipanteRepository;
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
    public IParticipanteRepository participanteRepository;   
    
    public ParticipanteService() {
    }
    
    public void saveParticipante(Participante usuario){
        try{
            participanteRepository.save(usuario);
        }catch(Exception e){
            System.out.println(e);            
        }
    }
    
    public void deleteParticipante(Participante usuario){
        participanteRepository.delete(usuario);
    }        
    
    public void updateParticipante(Participante usuario){
        participanteRepository.saveAndFlush(usuario);
    }
    
    public Participante findOneParticipante(Integer codigo){
        return participanteRepository.findOne(codigo);
    }
    
    public List<Participante> findAllParticipantes() {
        try{
            return participanteRepository.findAll(where(specificationAll()));
        }catch(Exception e){
            System.out.println(e);            
        }
        return null;
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

    @Transactional(timeout = 10)
    public Participante findByCpf(String cpf){        
        try{
            Participante c = participanteRepository.findByCpf(cpf);            
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

}
