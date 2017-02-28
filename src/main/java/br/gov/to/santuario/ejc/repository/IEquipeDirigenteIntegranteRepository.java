package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.EquipeDirigenteIntegrante;
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
public interface IEquipeDirigenteIntegranteRepository extends JpaRepository<EquipeDirigenteIntegrante, Serializable>, JpaSpecificationExecutor {
 
    @Query(value =  "SELECT eei.id, eei.seguidor_id,  eei.equipe_dirigente_id, p.nome, " +
                    " CASE WHEN s.tio = true then 'tio' ELSE 'jovem' END AS tipo, '' AS funcao " +
                    " FROM segueme.seguidor s, segueme.participante p, segueme.equipe_dirigente_integrante eei " +
                    //" --LEFT JOIN segueme.funcao f ON f.id = eei.funcao_id" +
                    " WHERE eei.equipe_dirigente_id = ?1 " +
                    " AND eei.seguidor_id = s.id  " +
                    " AND p.id = s.participante_id " +
                    " ORDER BY funcao, tipo, nome", nativeQuery = true)    
    ArrayList<EquipeDirigenteIntegrante> findSeguidoresDaEquipe(Integer idEquipeDirigente);
}
