package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.Conselho;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flavio.madureira
 */
@Repository
public interface IConselhoRepository extends JpaRepository<Conselho, Serializable>, JpaSpecificationExecutor {
    
}
