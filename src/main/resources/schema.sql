CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    data_cadastro TIMESTAMP,
    ativo BOOLEAN
);

CREATE TABLE restaurantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    categoria VARCHAR(100),
    endereco VARCHAR(255),
    telefone VARCHAR(20),
    taxa_entrega DECIMAL(10, 2),
    avaliacao DECIMAL(3, 1),
    ativo BOOLEAN
);

CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255),
    preco DECIMAL(10, 2),
    categoria VARCHAR(100),
    disponivel BOOLEAN,
    restaurante_id BIGINT
);

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_pedido VARCHAR(20),
    data_pedido TIMESTAMP,
    status VARCHAR(50),
    valor_total DECIMAL(10, 2),
    observacoes VARCHAR(255),
    cliente_id BIGINT,
    restaurante_id BIGINT
);

CREATE TABLE itens_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantidade INT,
    preco_unitario DECIMAL(10, 2),
    subtotal DECIMAL(10, 2),
    pedido_id BIGINT,
    produto_id BIGINT
);