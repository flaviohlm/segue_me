package br.gov.to.santuario.ejc.repository;

import br.gov.to.santuario.ejc.domain.Seguidor;
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
public interface ISeguidorRepository extends JpaRepository<Seguidor, Serializable>, JpaSpecificationExecutor {
    @Query(value =  "SELECT s.id, s.participante_id, s.tio, p.nome, 'coordenador' AS tipo, '1' AS ordem " +
                    "FROM segueme.seguidor s, segueme.participante p, segueme.encontro_equipe_integrantes eei " +
                    "WHERE eei.coordenador IS TRUE " +
                    "AND eei.encontro_equipe_id = ?1 " +
                    "AND p.id = s.participante_id " +
                    "AND eei.seguidor_id = s.id " +   
                    "UNION " +
                    "SELECT s.id, s.participante_id, s.tio, p.nome, 'seguidor' AS tipo, '2' AS ordem "+
                    "FROM segueme.seguidor s, segueme.participante p, segueme.encontro_equipe_integrantes eei " +
                    "WHERE (eei.coordenador IS FALSE OR eei.coordenador IS NULL) AND eei.encontro_equipe_id = ?1 AND p.id = s.participante_id AND eei.seguidor_id = s.id "+
                    "UNION " +
                    "SELECT s.id, s.participante_id, s.tio, p.nome, 'seguidor' AS tipo, '3' AS ordem " +
                    "FROM segueme.seguidor s, segueme.participante p " +
                    "WHERE s.id NOT IN ( SELECT eei.seguidor_id FROM segueme.encontro_equipe_integrantes eei INNER JOIN segueme.encontro_equipe ee ON ee.id = eei.encontro_equipe_id WHERE ee.encontro_id = ?2 AND eei.convite_aceito = true) " + 
                    "AND s.id NOT IN ( SELECT eei.seguidor_id FROM segueme.encontro_equipe_integrantes eei INNER JOIN segueme.encontro_equipe ee ON ee.id = eei.encontro_equipe_id WHERE ee.id = ?1) "+
                    "AND s.id NOT IN ( SELECT ec.seguidor_padrinho_id FROM segueme.encontro_circulo ec WHERE encontro_id = 3 AND seguidor_padrinho_id IS NOT NULL ) " +
                    "AND s.id NOT IN ( SELECT ec.seguidor_madrinha_id FROM segueme.encontro_circulo ec WHERE encontro_id = 3 AND seguidor_madrinha_id IS NOT NULL ) "+
                    "AND p.id = s.participante_id " +
                    "ORDER BY ordem, tipo, nome", nativeQuery = true)    
    ArrayList<Seguidor> findSeguidoresDisponiveis(Integer idEncontroEquipe, Integer idEncontro);
    
    
    @Query(value =  "SELECT s.id, s.participante_id, s.tio, p.nome\n" +
                    "FROM segueme.seguidor s, segueme.participante p \n" +
                    "WHERE s.id IN ( SELECT eei.seguidor_id FROM segueme.encontro_equipe_integrantes eei INNER JOIN segueme.encontro_equipe ee ON ee.id = eei.encontro_equipe_id INNER JOIN segueme.equipe e ON e.id = ee.equipe_id WHERE ee.encontro_id = ?1 AND e.descricao ILIKE 'c_rculo%' ) \n" +                    
                    "AND p.id = s.participante_id \n" +
                    "AND p.sexo = ?2\n " +
                    "AND s.tio = ?3\n "+
                    "ORDER BY nome", nativeQuery = true)    
    ArrayList<Seguidor> findSeguidoresPadrinhosDisponiveis(Integer idEncontro, String sexo, Boolean tio);
    
    @Query(value =  "SELECT s.id, s.participante_id, p.nome, " +
                    " CASE WHEN s.tio = true then 'tio' ELSE 'jovem' END AS tipo, f.descricao AS funcao, '1' AS ordem" +
                    " FROM segueme.seguidor s, segueme.participante p, segueme.equipe_dirigente_integrante eei" +
                    " LEFT JOIN segueme.funcao f ON f.id = eei.funcao_id" +
                    " WHERE eei.equipe_dirigente_id = ?1" +
                    " AND eei.seguidor_id = s.id " +
                    " AND p.id = s.participante_id " + 
                    " UNION" +
                    " SELECT s.id, s.participante_id, p.nome, " +
                    " CASE WHEN s.tio = true then 'tio' ELSE 'jovem' END AS tipo, '' AS funcao, '2' AS ordem" +
                    " FROM segueme.seguidor s, segueme.participante p" +
                    " WHERE p.id = s.participante_id " +
                    " AND s.id NOT IN (SELECT eei.seguidor_id FROM segueme.equipe_dirigente_integrante eei WHERE eei.equipe_dirigente_id = ?1)" +
                    " ORDER BY ordem, funcao, tipo desc, nome", nativeQuery = true)    
    ArrayList<Seguidor> findSeguidoresEquipeDirigente(Integer id);
    
    @Query(value =  "SELECT s.id, s.participante_id, p.nome,  \n" +
"                     CASE WHEN s.tio = true then 'tio' ELSE 'jovem' END AS tipo, f.descricao AS funcao, '1' AS ordem \n" +
"                     FROM segueme.seguidor s, segueme.participante p, segueme.conselho_integrante eei \n" +
"                     LEFT JOIN segueme.funcao f ON f.id = eei.funcao_id \n" +
"                     WHERE eei.conselho_id = ?1 \n" +
"                     AND eei.seguidor_id = s.id  \n" +
"                     AND p.id = s.participante_id   \n" +
"                     UNION \n" +
"                     SELECT s.id, s.participante_id, p.nome,  \n" +
"                     CASE WHEN s.tio = true then 'tio' ELSE 'jovem' END AS tipo, '' AS funcao, '2' AS ordem \n" +
"                     FROM segueme.seguidor s, segueme.participante p \n" +
"                     WHERE p.id = s.participante_id  \n" +
"                     AND s.id NOT IN (SELECT eei.seguidor_id FROM segueme.conselho_integrante eei WHERE eei.conselho_id = ?1) \n" +
"                     ORDER BY ordem, funcao, tipo desc, nome", nativeQuery = true)    
    ArrayList<Seguidor> findSeguidoresConselho(Integer id);
    
    
}
