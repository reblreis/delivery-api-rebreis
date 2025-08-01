-- ========================================
-- RESET DE TABELAS (opcional para testes)
-- ========================================
-- CUIDADO: Isso apaga os dados existentes!
-- DELETE FROM produto;
-- DELETE FROM usuario;
-- DELETE FROM restaurante;

-- ========================================
-- INSERIR RESTAURANTES
-- ========================================

INSERT INTO restaurante (id, nome, endereco, telefone, categoria, ativo, taxa_entrega, data_criacao) VALUES
(1, 'Pizza Palace', 'Rua das Pizzas, 123', '(11) 1234-5678', 'Italiana', true, 5.90, '2024-07-18T12:00:00'),
(2, 'Burger King', 'Av. dos Hambúrgueres, 456', '(11) 8765-4321', 'Fast Food', true, 3.50, '2024-07-18T12:00:00');

-- Ajustar sequência de IDs (se necessário)
ALTER TABLE restaurante ALTER COLUMN id RESTART WITH 3;

-- ========================================
-- INSERIR USUÁRIOS
-- ========================================
-- Senha hashada: "123456"

INSERT INTO usuario (id, nome, email, senha, role, ativo, data_criacao, restaurante_id) VALUES
(1, 'Admin Sistema', 'admin@delivery.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'ADMIN', true, CURRENT_TIMESTAMP, null),
(2, 'João Cliente', 'joao@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'CLIENTE', true, CURRENT_TIMESTAMP, null),
(3, 'Maria Cliente', 'maria@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'CLIENTE', true, CURRENT_TIMESTAMP, null),
(4, 'Pizza Palace', 'pizza@palace.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'RESTAURANTE', true, CURRENT_TIMESTAMP, 1),
(5, 'Burger King', 'burger@king.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'RESTAURANTE', true, CURRENT_TIMESTAMP, 2),
(6, 'Carlos Entregador', 'carlos@entrega.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'ENTREGADOR', true, CURRENT_TIMESTAMP, null);

-- Ajustar sequência de IDs (se necessário)
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 10;

-- ========================================
-- INSERIR PRODUTOS
-- ========================================
INSERT INTO produto (id, nome, descricao, preco, categoria, disponivel, restaurante_id, estoque) VALUES
(1, 'Pizza Margherita', 'Pizza com molho de tomate, mussarela e manjericão', 35.90, 'Pizza', true, 1, 20),
(2, 'Pizza Pepperoni', 'Pizza com molho de tomate, mussarela e pepperoni', 42.90, 'Pizza', true, 1, 15),
(3, 'Whopper', 'Hambúrguer com carne grelhada, alface, tomate, cebola', 28.90, 'Hambúrguer', true, 2, 25),
(4, 'Big King', 'Dois hambúrgueres, alface, queijo, molho especial', 32.90, 'Hambúrguer', true, 2, 30);

-- Ajustar sequência de IDs (se necessário)
ALTER TABLE produto ALTER COLUMN id RESTART WITH 5;

-- ========================================
-- INSERIR PEDIDOS
-- ========================================
INSERT INTO pedido (id, cliente_id, restaurante_id, entregador_id, status, total, data_criacao)
VALUES
(1, 2, 1, 6, 'ENTREGUE', 78.80, '2024-07-28 18:30:00'), -- João pediu 2 pizzas
(2, 3, 2, 6, 'CRIADO', 61.80, '2024-07-29 12:00:00');    -- Maria pediu 2 hambúrgueres

ALTER TABLE pedido ALTER COLUMN id RESTART WITH 3;

-- ========================================
-- INSERIR ITENS DE PEDIDO
-- ========================================
INSERT INTO pedido_item (id, pedido_id, produto_id, quantidade, preco_unitario, subtotal)
VALUES
(1, 1, 1, 1, 35.90, 35.90), -- Pizza Margherita
(2, 1, 2, 1, 42.90, 42.90), -- Pizza Pepperoni
(3, 2, 3, 1, 28.90, 28.90), -- Whopper
(4, 2, 4, 1, 32.90, 32.90); -- Big King

ALTER TABLE pedido_item ALTER COLUMN id RESTART WITH 5;

-- ========================================
-- INSERIR ENDEREÇOS DE ENTREGA
-- ========================================
INSERT INTO endereco_entrega (id, pedido_id, rua, numero, bairro, cidade, uf, cep, complemento)
VALUES
(1, 1, 'Rua das Laranjeiras', '100', 'Centro', 'São Paulo', 'SP', '01000-000', 'Apto 12'),
(2, 2, 'Av. Brasil', '2000', 'Jardins', 'São Paulo', 'SP', '01400-000', 'Casa amarela');

ALTER TABLE endereco_entrega ALTER COLUMN id RESTART WITH 3;