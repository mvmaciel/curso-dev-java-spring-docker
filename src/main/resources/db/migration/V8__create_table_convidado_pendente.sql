CREATE TABLE `curso-database`.convidado_pendente (
	id BIGINT auto_increment NOT NULL PRIMARY KEY,
	tarefa_id BIGINT NOT NULL,
	convidado_nome varchar(100) NOT NULL,
	convidado_email varchar(100) NULL,
	CONSTRAINT convidados_pendentes_tarefa_FK FOREIGN KEY (tarefa_id) REFERENCES `curso-database`.tarefa(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
