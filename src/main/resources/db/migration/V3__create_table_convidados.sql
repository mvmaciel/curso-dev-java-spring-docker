CREATE TABLE convidados (
  Id BIGINT PRIMARY KEY,
  tarefaId BIGINT,
  usuarioId BIGINT,
  FOREIGN KEY (tarefaId) REFERENCES tarefa(Id),
  FOREIGN KEY (usuarioId) REFERENCES usuario(Id)
);