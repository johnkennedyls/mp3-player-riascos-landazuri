CREATE DATABASE MyWay;

USE MyWay;

CREATE TABLE Usuario(
id_usuario int PRIMARY KEY NOT NULL,
corr_usuario VARCHAR(250) NOT NULL,
nom_usuario VARCHAR(250) NOT NULL,
con_usuario VARCHAR(500) NOT NULL
);

SELECT * FROM Usuario;

INSERT INTO Usuario(id_usuario, corr_usuario, nom_usuario, con_usuario) VALUES (1, "test@gmail.com", "Juan", "contraseña");

CREATE TABLE `playlist` (
  `idPlaylist` INT NOT NULL,
  `nom_pl` VARCHAR(250) NOT NULL,
  `songs_pl` BLOB NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`idPlaylist`),
  INDEX `idUsuario_FK_idx` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `idUsuario_FK`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
SELECT * FROM Playlist;
    
UPDATE Usuario SET id_usuario = 1 WHERE (id_usuario = 2);


    
    
    