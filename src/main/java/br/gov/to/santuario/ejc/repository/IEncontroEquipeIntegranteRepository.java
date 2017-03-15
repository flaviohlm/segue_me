package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
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
public interface IEncontroEquipeIntegranteRepository extends JpaRepository<EncontroEquipeIntegrante, Serializable>, JpaSpecificationExecutor {
    
    @Query(value =  "SELECT eei.id, eei.seguidor_id, eei.coordenador, eei.encontro_equipe_id, eei.convite_aceito, eei.observacoes, eei.desistiu, eei.aptidao_coordenacao, '1' AS ordem, p.nome, CASE WHEN s.tio = true then 'tio' ELSE 'jovem' END AS tipo, CASE WHEN eei.convite_aceito = true THEN 'ACEITO' WHEN eei.convite_aceito = false THEN 'RECUSADO' ELSE 'A CONVIDAR' END AS convite " +
                    "FROM segueme.seguidor s, segueme.participante p, segueme.encontro_equipe_integrantes eei " +
                    "WHERE eei.coordenador IS TRUE " +
                    "AND eei.encontro_equipe_id = ?1 " +
                    "AND p.id = s.participante_id " +
                    "AND eei.seguidor_id = s.id " +   
                    "UNION " +
                    "SELECT eei.id, eei.seguidor_id, eei.coordenador, eei.encontro_equipe_id, eei.convite_aceito, eei.observacoes, eei.desistiu, eei.aptidao_coordenacao, '2' AS ordem, p.nome, CASE WHEN s.tio = true then 'tio' ELSE 'jovem' END AS tipo, CASE WHEN eei.convite_aceito = true THEN 'ACEITO' WHEN eei.convite_aceito = false THEN 'RECUSADO' ELSE 'A CONVIDAR' END AS convite " +
                    "FROM segueme.seguidor s, segueme.participante p, segueme.encontro_equipe_integrantes eei " +
                    "WHERE (eei.coordenador IS FALSE OR eei.coordenador IS NULL) AND eei.encontro_equipe_id = ?1 AND p.id = s.participante_id AND eei.seguidor_id = s.id "+                    
                    "ORDER BY ordem, tipo DESC, convite, nome", nativeQuery = true)    
    ArrayList<EncontroEquipeIntegrante> findSeguidoresDaEquipe(Integer idEncontroEquipe);
    
}
