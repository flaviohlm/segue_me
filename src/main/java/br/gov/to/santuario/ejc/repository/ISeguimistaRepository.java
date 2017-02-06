package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.Seguimista;
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
public interface ISeguimistaRepository extends JpaRepository<Seguimista, Serializable>, JpaSpecificationExecutor {
    
    @Query(value =  "SELECT s.id, s.participante_id, s.encontro_id, p.nome\n" +
                    " FROM segueme.seguimista s, segueme.participante p\n" +
                    " WHERE p.id = s.participante_id\n" +
                    " AND s.encontro_id = ?1\n" +
                    " AND s.id NOT IN(SELECT ecs.seguimista_id \n" +
                    " FROM segueme.encontro_circulo_seguimista ecs \n" +
                    " WHERE encontro_circulo_id != ?2)\n" +
                    " ORDER BY p.data_nascimento", nativeQuery = true)    
    ArrayList<Seguimista> findSeguimistasDisponiveis(Integer idEncontro, Integer encontroCirculoId);
    
}
