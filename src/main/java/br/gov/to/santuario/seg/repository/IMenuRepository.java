package br.gov.to.santuario.seg.repository;

import br.gov.to.santuario.seg.domain.Menu;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author flavio.madureira
 */
public interface IMenuRepository extends JpaRepository<Menu, Integer>{
    
    public final static String FIND_BY_ID = "SELECT m "
            + "FROM Menu m "
            + "WHERE m.id = :id AND m.excluido=FALSE";
    
    public final static String FIND_BY_ATIVO = "SELECT m "
            + "FROM Menu m "
            + "WHERE m.excluido=FALSE";
    
    public final static String FIND_ALL_MENU = "SELECT m "
            + "FROM Menu m "
            + "ORDER BY m.nivel, m.ordem, m.id";
    
    public final static String FIND_BY_NIVEL = "SELECT m "
            + "FROM Menu m "
            + "WHERE m.nivel = :nivel "
            + "ORDER BY m.ordem, m.id";
    
    public final static String FIND_BY_PAI = "SELECT m "
            + "FROM Menu m "
            + "WHERE m.pai.id = :idPai AND m.ordem IS NOT NULL "
            + "ORDER BY m.ordem DESC";
    
    public final static String FIND_BY_PAI_ASC = "SELECT m "
            + "FROM Menu m "
            + "WHERE m.pai.id = :idPai AND m.ordem IS NOT NULL "
            + "ORDER BY m.ordem";
    
    public final static String FIND_BY_PAI_ORDEM = "SELECT m "
            + "FROM Menu m "
            + "WHERE m.pai.id = :idPai AND m.ordem = :ordem ";

    @Query(value = "  SELECT mo.icone, mo.id, mo.descricao,  mo.url, mo.nivel, mo.pai,  mo.ordem  FROM segueme.menu as mo, segueme.menu_perfil mu"
            + "	WHERE mo.id = mu.menu_id AND mu.perfil_id = ?1 AND mo.nivel > 0 ORDER BY ordem, url; ", nativeQuery = true)    
    ArrayList<Menu> findByPerfil(Integer id);    
    
    @Query(value = "  SELECT mo.icone, mo.id, mo.descricao,  mo.url, mo.nivel, mo.pai,  mo.ordem  FROM segueme.menu as mo, segueme.menu_perfil mu"
            + "	WHERE mo.id = mu.menu_id AND mo.excluido=FALSE AND mu.perfil_id = ?1 ORDER BY ordem, mo.pai; ", nativeQuery = true)    
    ArrayList<Menu> findByMenuNivel(Integer id);
    
    @Query(value = "SELECT id, descricao, pai, observacoes, excluido, ordem, ativo, nivel, url, icone FROM segueme.menu " 
                 + "WHERE url LIKE ?1; ", nativeQuery = true)    
    ArrayList<Menu> findByUrl(String url); 
    
    @Query(FIND_BY_ID)
    public Menu findMenuId(@Param("id") Integer id);
    
    @Query(FIND_BY_ATIVO)
    public List<Menu> findByAtivo(); 
    
    @Query(FIND_ALL_MENU)
    public List<Menu> findAllMenu(); 
    
    @Query(FIND_BY_NIVEL)
    public List<Menu> findByNivel(@Param("nivel") Integer nivel);
    
    @Query(FIND_BY_PAI)
    public List<Menu> findByPai(@Param("idPai") Integer idPai);
    
    @Query(FIND_BY_PAI_ORDEM)
    public List<Menu> findByPaiOrdem(@Param("idPai") Integer idPai, @Param("ordem") Integer ordem);

    @Query(FIND_BY_PAI_ASC)
    public List<Menu> findByPaiAsc(@Param("idPai") Integer idPai);
    
}
