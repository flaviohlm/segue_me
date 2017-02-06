package br.gov.to.santuario.seg.repository;

import br.gov.to.santuario.seg.domain.Participante;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author flavio.madureira
 */
public interface IParticipanteRepository extends JpaRepository<Participante, Serializable> , JpaSpecificationExecutor {

    public Participante findByCpf(@Param("cpf")String cpf);    
    
}
