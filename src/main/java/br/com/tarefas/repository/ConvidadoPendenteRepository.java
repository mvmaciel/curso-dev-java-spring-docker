package br.com.tarefas.repository;

import br.com.tarefas.entity.ConvidadoPendente;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConvidadoPendenteRepository extends JpaRepository<ConvidadoPendente, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE convidado_pendente c SET c.keycloak_id = :keycloakId WHERE c.convidado_email = :email", nativeQuery = true)
    int updateKeycloakIdByEmail(@Param("keycloakId") String keycloakId, @Param("email") String email);

    List<ConvidadoPendente> findByKeycloakId(String userId);
    @Query(value = "select * from convidado_pendente c where c.convidado_email =:email and c.keycloak_id is not null limit 1", nativeQuery = true)
    List<ConvidadoPendente> findConvidadosPendentesKeyCloak(@Param("email") String email);
}
