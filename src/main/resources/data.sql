INSERT INTO categorias (nome, descricao, ativo)
VALUES ('Smartphones', 'Celulares e acessórios.', true);

INSERT INTO categorias (nome, descricao, ativo)
VALUES ('Notebooks', 'Notebooks e ultrabooks.', true);

INSERT INTO categorias (nome, descricao, ativo)
VALUES ('Áudio', 'Fones de ouvido e caixas de som.', true);

INSERT INTO produtos (nome, descricao, marca, modelo, preco, ativo, categoria_id)
VALUES ('Galaxy S24', 'Smartphone 256GB', 'Samsung', 'SM-S921B', 3499.90, true,
        (SELECT id FROM categorias WHERE nome = 'Smartphones'));

INSERT INTO produtos (nome, descricao, marca, modelo, preco, ativo, categoria_id)
VALUES ('iPhone 15', 'Smartphone 128GB', 'Apple', 'A3090', 4999.00, true,
        (SELECT id FROM categorias WHERE nome = 'Smartphones'));

INSERT INTO produtos (nome, descricao, marca, modelo, preco, ativo, categoria_id)
VALUES ('Notebook Inspiron 15', 'Notebook 16GB RAM, SSD 512GB', 'Dell', 'I15-3520', 3899.00, true,
        (SELECT id FROM categorias WHERE nome = 'Notebooks'));

INSERT INTO produtos (nome, descricao, marca, modelo, preco, ativo, categoria_id)
VALUES ('Fone Tune 520BT', 'Fone de ouvido Bluetooth', 'JBL', 'T520BT', 279.90, true,
        (SELECT id FROM categorias WHERE nome = 'Áudio'));

INSERT INTO estoques (quantidade, produto_id)
VALUES (50, (SELECT id FROM produtos WHERE nome = 'Galaxy S24'));

INSERT INTO estoques (quantidade, produto_id)
VALUES (20, (SELECT id FROM produtos WHERE nome = 'iPhone 15'));

INSERT INTO estoques (quantidade, produto_id)
VALUES (10, (SELECT id FROM produtos WHERE nome = 'Notebook Inspiron 15'));

INSERT INTO estoques (quantidade, produto_id)
VALUES (100, (SELECT id FROM produtos WHERE nome = 'Fone Tune 520BT'));

INSERT INTO clientes (nome, email, senha, telefone, endereco)
VALUES ('Ana Souza', 'ana@email.com', '123456', '(71) 99999-0000', 'Rua A, 100 - Salvador/BA');

INSERT INTO clientes (nome, email, senha, telefone, endereco)
VALUES ('Bruno Lima', 'bruno@email.com', '654321', '(71) 98888-1111', 'Av. B, 200 - Lauro de Freitas/BA');

INSERT INTO administradores (nome, email, senha)
VALUES ('Admin Loja', 'admin@loja.com', 'admin123');

-- Usuarios de login (senhas com BCrypt): admin@loja.com / admin123  e  operador@loja.com / operador123
INSERT INTO usuarios (nome, email, senha, perfil)
VALUES ('Administrador', 'admin@loja.com', '$2a$10$L1cOt.wldfF8E6NcawvzReCtrDoLjW7SzWoPCkciSjqX0.XnQ7SR6', 'ADMIN');

INSERT INTO usuarios (nome, email, senha, perfil)
VALUES ('Operador', 'operador@loja.com', '$2a$10$3VmSbbMO3U4jqyoRKc5WAeQGQ9oae1cdrWv9.tJZHOtGKqyLh3nT6', 'OPERADOR');
