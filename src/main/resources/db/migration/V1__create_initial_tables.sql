-- Tabela de Planos
CREATE TABLE plano (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    duracao_em_dias INT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela de Usuários (Alunos)
CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    sobrenome VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    status ENUM ('ATIVO','INATIVO','PENDENTE'),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_vencimento DATE,
    plano_id BIGINT NOT NULL,
    -- Datas de auditoria automáticas pelo banco de dados
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),

    -- Constraints nomeadas para fácil identificação
    CONSTRAINT uk_usuario_email UNIQUE (email),
    CONSTRAINT uk_usuario_cpf UNIQUE (cpf),
    CONSTRAINT fk_usuario_plano FOREIGN KEY (plano_id) REFERENCES plano (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela de Pagamentos
CREATE TABLE pagamento (
    id BIGINT NOT NULL AUTO_INCREMENT,
    -- Chave estrangeira agora é NOT NULL para garantir integridade
    usuario_id BIGINT NOT NULL,
    valor_pago DECIMAL(10, 2) NOT NULL,
    data_pagamento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),

    -- Constraint nomeada
    CONSTRAINT fk_pagamento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
