package br.gov.to.santuario.seg.repository;

import br.gov.to.santuario.seg.domain.Perfil;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface IPerfilRepository extends JpaRepository<Perfil, Serializable>, JpaSpecificationExecutor {
     
}
