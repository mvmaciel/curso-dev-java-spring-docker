package br.com.tarefas.repository;

import br.com.tarefas.entity.Convidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ConvidadoRepository extends JpaRepository<Convidado, Long> {

    List<Convidado> findByUsuarioId(Long usuarioId);
}
