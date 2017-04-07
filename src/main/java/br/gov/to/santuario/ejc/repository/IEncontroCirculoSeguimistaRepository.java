package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.EncontroCirculoSeguimista;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flavio.madureira
 */
@Repository
public interface IEncontroCirculoSeguimistaRepository extends JpaRepository<EncontroCirculoSeguimista, Serializable>, JpaSpecificationExecutor {
    
    @Query(value =  "SELECT s.id, s.participante_id, s.encontro_id " +
                    " FROM segueme.seguimista s, segueme.participante p, segueme.encontro_circulo_seguimista eci " +
                    " WHERE eci.encontro_circulo_id = ?1 " +
                    " AND p.id = s.participante_id " +
                    " AND eci.seguimista_id = s.id " +
                    " ORDER BY nome", nativeQuery = true)    
    ArrayList<EncontroCirculoSeguimista> findSeguimistasCirculo(Integer idCirculo);
    
}
