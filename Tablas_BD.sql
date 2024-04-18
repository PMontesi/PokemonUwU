CREATE DATABASE POKEMONDB


CREATE TABLE POKEDEX(
NUM_POKEDEX INT PRIMARY KEY,
NOM_POKEMON VARCHAR(30) NOT NULL,
TIPO1 VARCHAR(20) NOT NULL,
TIPO2 VARCHAR(20),
IMAGEN_DELANTE VARCHAR(100) NOT NULL,
IMAGEN_DETRAS VARCHAR(100) NOT NULL,
IMAGEN_DELANTE_F VARCHAR(100),
IMAGEN_DETRAS_F VARCHAR(100),
SONIDO VARCHAR(100) NOT NULL,
NIVEL_EVOLUCION INT,
NUM_POKEDEX_EVO INT);

-- IMPORTANTE: Si todavía no se han hecho los inserts, no crear la clave foránea.
ALTER TABLE POKEDEX
ADD CONSTRAINT NUM_POKEDEX_EVO_FK 
	FOREIGN KEY (NUM_POKEDEX_EVO) 
	REFERENCES POKEDEX(NUM_POKEDEX);
	

CREATE TABLE ENTRENADOR(
ID_ENTRENADOR INT AUTO_INCREMENT PRIMARY KEY,
NOM_ENTRENADOR VARCHAR(20) UNIQUE NOT NULL,
PASS VARCHAR(20) NOT NULL,
POKEDOLLARS INT NOT NULL);


CREATE TABLE OBJETO(
ID_OBJETO INT PRIMARY KEY,
NOMBRE VARCHAR(20) NOT NULL,
ATAQUE INT,
AT_ESPECIAL INT,
DEFENSA INT,
DEF_ESPECIAL INT NOT NULL,
VELOCIDAD INT,
PRECIO INT NOT NULL);


CREATE TABLE MOCHILA(
ID_ENTREANDOR INT,
ID_OBJETO INT,
NUM_OBJETO INT NOT NULL);

ALTER TABLE MOCHILA
ADD CONSTRAINT MOCHILA_PK 
	PRIMARY KEY (ID_ENTREANDOR, ID_OBJETO),
ADD CONSTRAINT ID_ENTREANDOR_MOCHILA_FK 
	FOREIGN KEY (ID_ENTREANDOR) 
    REFERENCES ENTRENADOR(ID_ENTRENADOR),
ADD CONSTRAINT ID_OBJETO_MOCHILA_FK 
	FOREIGN KEY (ID_OBJETO) 
    REFERENCES OBJETO(ID_OBJETO);
	

CREATE TABLE POKEMON(
ID_POKEMON INT PRIMARY KEY,
NUM_POKEDEX INT,
ID_ENTRENADOR INT,
MOTE VARCHAR(30),
CAJA INT NOT NULL,
ATAQUE INT NOT NULL,
AT_ESPECIAL INT NOT NULL,
DEFENSA INT NOT NULL,
DEF_ESPECIAL INT NOT NULL,
VELOCIDAD INT NOT NULL,
NIVEL INT NOT NULL,
FERTILIDAD INT NOT NULL,
SEXO CHAR(1) NOT NULL,
ESTADO VARCHAR(20) NOT NULL,
EXPERIANCIA INT NOT NULL,
VITALIDAD INT NOT NULL,
ID_OBJETO INT);
	
ALTER TABLE POKEMON
ADD CONSTRAINT NUM_POKEDEX_FK 
	FOREIGN KEY (NUM_POKEDEX) 
    REFERENCES POKEDEX(NUM_POKEDEX),
ADD CONSTRAINT ID_ENTRENADOR_FK 
	FOREIGN KEY (ID_ENTRENADOR)
    REFERENCES ENTRENADOR(ID_ENTRENADOR),
ADD CONSTRAINT ID_OBJETO_FK
	FOREIGN KEY (ID_OBJETO) 
    REFERENCES OBJETO(ID_OBJETO);


CREATE TABLE MOVIMIENTOS(
ID_MOVIMIENTO INT PRIMARY KEY,
NOM_MOVIMIENTO VARCHAR(20) NOT NULL,
POTENCIA INT,
TIPO VARCHAR(20) NOT NULL,
ESTADO VARCHAR(20),
QUITA INT,
TURNOS INT,
MEJORA VARCHAR(20),
CANT_MEJORA INT,
NIVEL_APRENDIZAJE INT NOT NULL);


CREATE TABLE MOVIMIENTOS_POKEMON(
ID_MOVIMIENTO INT,
ID_POKEMON INT,
ACTIVO CHAR(1) NOT NULL);

ALTER TABLE MOVIMIENTOS_POKEMON
ADD CONSTRAINT MOVIMIENTOS_POKEMON_PK 
	PRIMARY KEY (ID_MOVIMIENTO, ID_POKEMON),
ADD CONSTRAINT ID_MOVIMIENTO_FK 
	FOREIGN KEY (ID_MOVIMIENTO) 
    REFERENCES MOVIMIENTOS(ID_MOVIMIENTO),
ADD CONSTRAINT ID_POKEMON_FK 
	FOREIGN KEY (ID_POKEMON) 
    REFERENCES POKEMON(ID_POKEMON);


CREATE TABLE COMBATE(
ID_COMBATE INT PRIMARY KEY,
FECHA_HORA TIMESTAMP NOT NULL,
ID_ENTRENADOR INT);


ALTER TABLE COMBATE
ADD CONSTRAINT ID_ENTREANDOR_COMBATE_FK 
	FOREIGN KEY (ID_ENTRENADOR) 
    REFERENCES ENTRENADOR(ID_ENTRENADOR);


CREATE TABLE TURNOS(
ID_TURNO INT PRIMARY KEY,
ACCION_ENTRENADOR VARCHAR (150) NOT NULL,
ACCION_RIVAL VARCHAR (150) NOT NULL,
ID_COMBATE INT);

ALTER TABLE TURNOS
ADD CONSTRAINT ID_COMBATE 
	FOREIGN KEY (ID_COMBATE) 
    REFERENCES COMBATE(ID_COMBATE);
	
