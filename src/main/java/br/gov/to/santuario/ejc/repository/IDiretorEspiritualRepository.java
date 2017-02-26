package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.DiretorEspiritual;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Flavio
 */
@Repository
public interface IDiretorEspiritualRepository extends JpaRepository<DiretorEspiritual, Serializable>, JpaSpecificationExecutor {
    
}
