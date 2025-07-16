CREATE TABLE foto (
    id_foto BIGINT NOT NULL AUTO_INCREMENT,
    imagen VARCHAR(255) NOT NULL,
    tipo VARCHAR(10) NOT NULL,       
    fecha_captura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE, 
    usuario_id BIGINT NOT NULL,                   
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario),
    PRIMARY KEY (id_foto) USING BTREE
);


