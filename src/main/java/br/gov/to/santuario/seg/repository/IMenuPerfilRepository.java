package br.gov.to.santuario.seg.repository;

import br.gov.to.santuario.seg.domain.MenuUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author flavio.madureira
 */
public interface IMenuPerfilRepository extends JpaRepository<MenuUsuario, Integer>{
    
}
