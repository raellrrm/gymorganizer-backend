
INSERT INTO plano (nome, valor, duracao_em_dias) VALUES ('Mensal', 99.90, 30);
INSERT INTO plano (nome, valor, duracao_em_dias) VALUES ('Trimestral', 270.00, 90);
INSERT INTO plano (nome, valor, duracao_em_dias) VALUES ('Anual', 1000.00, 365);

INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_criacao, data_vencimento, cpf, plano_id, ativo)
VALUES ('Ana', 'Silva', '1995-05-15', '11987654321', 'ana.silva@email.com', 'ATIVO', NOW(), CURDATE() + INTERVAL 15 DAY, '11122233344', 1, true);


INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_criacao, data_vencimento, cpf, plano_id, ativo)
VALUES ('Bruno', 'Costa', '1988-11-20', '21912345678', 'bruno.costa@email.com', 'PENDENTE', NOW(), CURDATE() - INTERVAL 10 DAY, '22233344455', 3, true);


INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_criacao, data_vencimento, cpf, plano_id, ativo)
VALUES ('Carla', 'Dias', '2001-02-10', '31955556666', 'carla.dias@email.com', 'PENDENTE', NOW(), NULL, '33344455566', 1, true);


INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_criacao, data_vencimento, cpf, plano_id, ativo)
VALUES ('Daniel', 'Souza', '1992-09-30', '41988776655', 'daniel.souza@email.com', 'INATIVO', '2024-01-15 10:00:00', '2025-01-15', '44455566677', 3, false);
