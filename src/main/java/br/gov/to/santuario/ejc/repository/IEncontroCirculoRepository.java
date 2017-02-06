package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.EncontroCirculo;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flavio.madureira
 */
@Repository
public interface IEncontroCirculoRepository extends JpaRepository<EncontroCirculo, Serializable>, JpaSpecificationExecutor {
    
}
