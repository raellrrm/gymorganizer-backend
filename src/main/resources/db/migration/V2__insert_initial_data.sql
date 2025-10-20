
INSERT INTO plano (nome, valor, duracao_em_dias) VALUES ('Mensal', 99.90, 30);
INSERT INTO plano (nome, valor, duracao_em_dias) VALUES ('Trimestral', 270.00, 90);
INSERT INTO plano (nome, valor, duracao_em_dias) VALUES ('Anual', 1000.00, 365);

INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Ana', 'Silva', '1995-05-15', '11987654321', 'ana.silva@email.com', 'ATIVO', CURDATE() + INTERVAL 5 DAY, '11122233344', 1, true);

INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Bruno', 'Costa', '1988-11-20', '21912345678', 'bruno.costa@email.com', 'PENDENTE', CURDATE() - INTERVAL 10 DAY, '22233344455', 3, true);

INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Carla', 'Dias', '2001-02-10', '31955556666', 'carla.dias@email.com', 'PENDENTE', NULL, '33344455566', 1, true);

INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Daniel', 'Souza', '1992-09-30', '41988776655', 'daniel.souza@email.com', 'INATIVO', '2025-01-15', '44455566677', 3, false);

INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Eduardo', 'Moreira', '1998-07-22', '51988887777', 'eduardo.moreira@email.com', 'ATIVO', CURDATE() + INTERVAL 45 DAY, '55566677788', 2, true);


INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Fernanda', 'Lima', '1991-03-12', '61977778888', 'fernanda.lima@email.com', 'PENDENTE', CURDATE() - INTERVAL 6 DAY, '66677788899', 1, true);

INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Gustavo', 'Borges', '1985-12-01', '71966665555', 'gustavo.borges@email.com', 'ATIVO', CURDATE() + INTERVAL 360 DAY, '77788899900', 3, true);


INSERT INTO usuario (nome, sobrenome, data_nascimento, telefone, email, status, data_vencimento, cpf, plano_id, ativo)
VALUES ('Helena', 'Santos', '2003-08-05', '81955554444', 'helena.santos@email.com', 'PENDENTE', NULL, '88899900011', 2, true);


INSERT INTO pagamento (usuario_id, valor_pago, data_pagamento) VALUES (1, 99.90, NOW() - INTERVAL 25 DAY);

INSERT INTO pagamento (usuario_id, valor_pago, data_pagamento) VALUES (2, 1000.00, NOW() - INTERVAL 375 DAY);
INSERT INTO pagamento (usuario_id, valor_pago, data_pagamento) VALUES (2, 1000.00, NOW() - INTERVAL 740 DAY); -- Pagamento mais antigo

INSERT INTO pagamento (usuario_id, valor_pago, data_pagamento) VALUES (5, 270.00, NOW() - INTERVAL 45 DAY);

INSERT INTO pagamento (usuario_id, valor_pago, data_pagamento) VALUES (6, 99.90, NOW() - INTERVAL 36 DAY);

INSERT INTO pagamento (usuario_id, valor_pago, data_pagamento) VALUES (7, 1000.00, NOW() - INTERVAL 5 DAY);
