INSERT INTO empresa (id, cnpj, data_criacao, razao_social)
			 VALUES (NEXTVAL('sq_empresa'), '82198127000121', CURRENT_DATE, 'Empresa Teste');
			 
INSERT INTO funcionario (id, cpf, data_criacao, email, nome, perfil, senha, empresa_id)
			 VALUES (NEXTVAL('sq_funcionario'), '06364560851', CURRENT_DATE,'admin@admin.com','ADMIN',
			 		'ROLE_ADMIN','$2a$10$DOXN44TaCPo7BBomqUVkt.D7F4PplGNY.BUnwqlpElQT/IeSkJV1.',
			 		(SELECT id FROM empresa WHERE cnpj = '82198127000121'));