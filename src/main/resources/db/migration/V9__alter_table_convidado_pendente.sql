ALTER TABLE `curso-database`.convidado_pendente ADD keycloak_id varchar(100) NULL;
CREATE INDEX convidado_pendente_convidado_email_IDX USING BTREE ON `curso-database`.convidado_pendente (convidado_email);
