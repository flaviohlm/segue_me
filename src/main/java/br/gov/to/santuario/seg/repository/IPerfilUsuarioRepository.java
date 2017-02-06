package br.gov.to.santuario.seg.repository;

import br.gov.to.santuario.seg.domain.PerfilUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author flavio.madureira
 */
public interface IPerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Serializable>, JpaSpecificationExecutor {
    
    @Query(value = "SELECT t1.id, t1.perfil_id, t1.participante_id " +
                   " FROM postgres.segueme.participante t0, postgres.segueme.perfil_usuario t1 " +
                   " WHERE t0.cpf = ?1 " +
                   " AND t0.id = t1.participante_id " +
                   " AND t0.password IS NOT NULL", nativeQuery = true)    
    ArrayList<PerfilUsuario> findAtivosByCpf(String cpf);
    
    public List<PerfilUsuario> findByCpf(@Param("cpf") String cpf);
}
