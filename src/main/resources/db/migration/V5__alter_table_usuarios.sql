ALTER TABLE `curso-database`.tarefa DROP FOREIGN KEY fk_tarefa_usuario;
ALTER TABLE `curso-database`.convidados DROP FOREIGN KEY convidados_ibfk_2;


ALTER TABLE `curso-database`.usuario MODIFY COLUMN Id bigint auto_increment NOT NULL;

ALTER TABLE `curso-database`.tarefa ADD CONSTRAINT fk_tarefa_usuario FOREIGN KEY (criadorId) REFERENCES usuario(id) ON DELETE CASCADE;
ALTER TABLE `curso-database`.convidados ADD CONSTRAINT convidados_ibfk_2 FOREIGN KEY (usuarioId) REFERENCES usuario(id) ON DELETE CASCADE;
