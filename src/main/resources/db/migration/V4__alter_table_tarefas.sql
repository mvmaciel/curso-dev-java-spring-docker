ALTER TABLE tarefa
ADD COLUMN criadorId BIGINT;

ALTER TABLE tarefa
ADD CONSTRAINT fk_tarefa_usuario
FOREIGN KEY (criadorId) REFERENCES usuario(Id);