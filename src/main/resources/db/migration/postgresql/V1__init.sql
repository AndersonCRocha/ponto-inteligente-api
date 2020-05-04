CREATE TABLE empresa (
	id BIGINT PRIMARY KEY,
	razao_social VARCHAR(255) NOT NULL,
	cnpj VARCHAR(255) NOT NULL,
	data_criacao TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
);

CREATE TABLE funcionario (
	id BIGINT PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	cpf VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	senha VARCHAR(255) NOT NULL,
	qtd_horas_trabalho_dia DOUBLE PRECISION DEFAULT NULL,
	qtd_horas_almoco DOUBLE PRECISION DEFAULT NULL,
	valor_hora DOUBLE PRECISION DEFAULT NULL,
	perfil VARCHAR(255) NOT NULL,
	data_criacao TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	empresa_id BIGINT DEFAULT NULL,
	CONSTRAINT fk_funcionario_emmpresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

CREATE TABLE lancamento (
	id BIGINT PRIMARY KEY,
	data TIMESTAMP NOT NULL,
	data_criacao TIMESTAMP NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	localizacao VARCHAR(255) NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	data_atualizacao TIMESTAMP,
	funcionario_id BIGINT DEFAULT NULL,
	CONSTRAINT fk_lancamento_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);

