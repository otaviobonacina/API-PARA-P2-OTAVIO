USE application;

CREATE TABLE usuarios(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_de_usuario VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);