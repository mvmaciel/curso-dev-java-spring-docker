ALTER TABLE `curso-database`.tarefa DROP FOREIGN KEY fk_tarefa_usuario;
ALTER TABLE `curso-database`.tarefa DROP COLUMN criadorId;
ALTER TABLE `curso-database`.convidados DROP FOREIGN KEY convidados_ibfk_2;
ALTER TABLE `curso-database`.convidados DROP FOREIGN KEY convidados_ibfk_1;
ALTER TABLE `curso-database`.convidados DROP COLUMN usuarioId;
ALTER TABLE `curso-database`.convidados DROP COLUMN tarefaId;


ALTER TABLE tarefa
ADD COLUMN criador_id BIGINT;

ALTER TABLE convidados
ADD COLUMN usuario_id BIGINT;

ALTER TABLE convidados
ADD COLUMN tarefa_id BIGINT;



ALTER TABLE `curso-database`.tarefa ADD CONSTRAINT fk_tarefa_usuario FOREIGN KEY (criador_id) REFERENCES usuario(id) ON DELETE CASCADE;
ALTER TABLE `curso-database`.convidados ADD CONSTRAINT convidados_ibfk_2 FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE;

