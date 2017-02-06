package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.Paroquia;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flavio.madureira
 */
@Repository
public interface IParoquiaRepository extends JpaRepository<Paroquia, Serializable>, JpaSpecificationExecutor {
    
}
