/* IMPORTANTE: Borrar la clave foránea antes de hacer los inserts. Phpmyadmin no admite DISABLE/ENABLE.
Otra opción es insertar primero los pokemons que no evolucionan e ir insertando las prevoluciones, es decir,
insertar primero a Venusaur, después Ivysaur y por último Bulbasaur.
*/
ALTER TABLE POKEDEX DROP INDEX NUM_POKEDEX_EVO_FK; 

INSERT INTO POKEDEX (NUM_POKEDEX, NOM_POKEMON, TIPO1, TIPO2, IMAGEN_DELANTE, IMAGEN_DETRAS, IMAGEN_DELANTE_F, IMAGEN_DETRAS_F, SONIDO, NIVEL_EVOLUCION, NUM_POKEDEX_EVO) VALUES
(1, 'Bulbasaur', 'Planta', 'Veneno', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\bulbasaur-front.png',  	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\bulbasur-back.png', NULL, NULL, 'sonido1.mp3', 16, 2),
(2, 'Ivysaur', 'Planta', 'Veneno', 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\ivysaur-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\ivysaur-back.png', NULL, NULL, 'sonido2.mp3', 32, 3),
(3, 'Venusaur', 'Planta', 'Veneno', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\venusaur-m-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\venusaur-m-back.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\venusaur-f-front.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\venusaur-f-back.png', 'sonido3.mp3', NULL, NULL),
(4, 'Charmander', 'Fuego', NULL, 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\charmander-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\charmander-back.png', NULL, NULL, 'sonido4.mp3', 16, 5),
(5, 'Charmeleon', 'Fuego', NULL, 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\charmeleon-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\charmeleon-back.png', NULL, NULL, 'sonido5.mp3', 36, 6),
(6, 'Charizard', 'Fuego', 'Volador', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\charizard-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\charizard-back.png', NULL, NULL, 'sonido6.mp3', NULL, NULL),
(7, 'Squirtle', 'Agua', NULL, 			'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\squirtle-front.png',        'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\squirtle-back.png', NULL, NULL, 'sonido7.mp3', 16, 8),
(8, 'Wartortle', 'Agua', NULL, 			'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\wartortle-front.png',  	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\wartortle-back.png', NULL, NULL, 'sonido8.mp3', 36, 9),
(9, 'Blastoise', 'Agua', NULL, 			'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\blastoise-front.png',  	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\blastoise-back.png', NULL, NULL, 'sonido9.mp3', NULL, NULL),
(10, 'Caterpie', 'Bicho', NULL, 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\caterpie-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\caterpie-back.png', NULL, NULL, 'sonido10.mp3', 7, 11),
(11, 'Metapod', 'Bicho', NULL, 			'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\metapod-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\metapod-back.png', NULL, NULL, 'sonido11.mp3', 10, 12),
(12, 'Butterfree', 'Bicho', 'Volador', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\butterfree-m-front.png',    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\butterfree-m-back.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\butterfree-f-front.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\butterfree-f-back.png', 'sonido12.mp3', NULL, NULL),
(13, 'Weedle', 'Bicho', 'Veneno', 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\weedle-front.png', 		    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\weedle-back.png', NULL, NULL, 'sonido13.mp3', 7, 14),
(14, 'Kakuna', 'Bicho', 'Veneno', 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\kakuna-front.png', 		    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\kakuna-back.png', NULL, NULL, 'sonido14.mp3', 10, 15),
(15, 'Beedrill', 'Bicho', 'Veneno', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\beedrill-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\beedrill-back.png', NULL, NULL, 'sonido15.mp3', NULL, NULL),
(16, 'Pidgey', 'Normal', 'Volador', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pidgey-front.png', 		    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pidgey-back.png', NULL, NULL, 'sonido16.mp3', 18, 17),
(17, 'Pidgeotto', 'Normal', 'Volador', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pidgeotto-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pidgeotto-back.png', NULL, NULL, 'sonido17.mp3', 36, 18),
(18, 'Pidgeot', 'Normal', 'Volador', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pidgeot-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pidgeot-back.png', NULL, NULL, 'sonido18.mp3', NULL, NULL),
(19, 'Rattata', 'Normal', NULL, 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\rattata-m-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\rattata-m-back.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\rattata-f-front.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\rattata-f-back.png', 'sonido19.mp3', 20, 20),
(20, 'Raticate', 'Normal', NULL, 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raticate-m-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raticate-m-back.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raticate-f-front.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raticate-f-back.png', 'sonido20.mp3', NULL, NULL),
(21, 'Spearow', 'Normal', 'Volador', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\spearow-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\spearow-back.png', NULL, NULL, 'sonido21.mp3', 20, 22),
(22, 'Fearow', 'Normal', 'Volador', 	'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\fearow-front.png', 		    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\fearow-back.png', NULL, NULL, 'sonido22.mp3', NULL, NULL),
(23, 'Ekans', 'Veneno', NULL, 			'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\ekans-front.png', 	        'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\ekans-back.png', NULL, NULL, 'sonido23.mp3', 22, 24),
(24, 'Arbok', 'Veneno', NULL, 			'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\arbok-front.png', 	        'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\arbok-back.png', NULL, NULL, 'sonido24.mp3', NULL, NULL),
(25, 'Pikachu', 'Eléctrico', NULL, 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pikachu-m-front.png',  	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pikachu-m-back.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pikachu-f-front.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\pikachu-f-back.png', 'sonido25.mp3', 28, 26),
(26, 'Raichu', 'Eléctrico', NULL, 		'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raichu-m-front.png', 	    'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raichu-m-back.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raichu-f-front.png', 'src\\main\\resources\\es\\cesur\\progprojectpok\\images\\pokemons\\raichu-f-back.png', 'sonido26.mp3', NULL, NULL);

-- IMPORTANTE: Añadir de nuevo al clave foránea al terminar los inserts si se ha elimnado.
ALTER TABLE POKEDEX
ADD CONSTRAINT NUM_POKEDEX_EVO_FK 
	FOREIGN KEY (NUM_POKEDEX_EVO) 
	REFERENCES POKEDEX(NUM_POKEDEX);


INSERT INTO ENTRENADOR (ID_ENTRENADOR, NOM_ENTRENADOR, PASS, POKEDOLLARS) VALUES (1, 'Paco', 123, 999999);


INSERT INTO MOVIMIENTOS (ID_MOVIMIENTO, NOM_MOVIMIENTO, TIPO, MEJORA, CANT_MEJORA, TURNOS,  NIVEL_APRENDIZAJE, PP_MAX) VALUES
(3, 'Danza espada', 'NORMAL', 'ATAQUE', 100, 5,	1, 20),
(10, 'Refugio', 'AGUA', 'DEFENSA', 50, 5, 1, 15),
(18, 'Luminicola', 'BICHO', 'AT_ESP', 100, 5, 1, 10),
(24, 'Danza dragón', 'DRAGON', 'VELOCIDAD', 100, 2, 1, 5),
(25, 'Afilagarras', 'DRAGON', 'ATAQUE', 150, 2, 1, 5),
(31, 'Carga', 'ELECTRICO', 'AT_ESP', 100, 5, 1, 5),
(41, 'Entrar en calor','FUEGO',  'AT_ESP', 150, 3, 1, 5),
(42, 'Muro de llamas', 'FUEGO', 'DEF_ESP', 50, 5, 1, 15),
(43, 'Neblina', 'HIELO', 'VELOCIDAD', 100, 5, 1, 10),
(44, 'Amadura helada', 'HIELO', 'DEFENSA', 100, 5, 1, 10),
(53, 'Estiramiento', 'LUCHA', 'ATAQUE', 100, 5, 1, 15),
(54, 'Concentración', 'LUCHA', 'DEFENSA', 150, 5, 1, 15),
(55, 'Carrerilla', 'LUCHA', 'VELOCIDAD', 200, 2, 1, 5),
(56, 'Afilar', 'NORMAL', 'ATAQUE', 50, 3, 1, 15),
(65, 'Desarrollo', 'NORMAL', 'AT_ESPECIAL', 50, 3, 1, 15),
(66, 'Fortaleza', 'NORMAL', 'DEFENSA', 50, 3, 1, 15),
(77, 'Agilidad', 'PSIQUICO', 'VELOCIDAD', 150, 3, 1, 5),
(78, 'Barrera', 'PSIQUICO', 'DEFENSA', 150, 3, 1, 5),
(79, 'Escudo', 'PSIQUICO', 'DEF_ESP', 150, 3, 1, 5),
(80, 'Kinético', 'PSIQUICO', 'ATAQUE', 200, 2, 1, 5),
(88, 'Rodar', 'ROCA', 'ATAQUE', 150, 4, 1, 10),
(92, 'Cuerpo arena', 'TIERRA', 'DEFENSA', 100, 6, 1, 20),
(94, 'Armadura ácida', 'VENENO', 'DEF_ESP', 100, 4, 1, 20),
(101, 'Espejo', 'VOLADOR', 'DEF_ESP', 150, 5, 1, 5);

INSERT INTO MOVIMIENTOS (ID_MOVIMIENTO, NOM_MOVIMIENTO, TIPO, ESTADO, TURNOS,  NIVEL_APRENDIZAJE, PP_MAX) VALUES
(4, 'Canto', 'NORMAL', 'DORMIDO', 3, 1, 15),
(11, 'Tenaza', 'AGUA', 'PARALIZADO', 3, 1, 15),
(12, 'Chupavidas', 'BICHO', 'DRENADORAS', 5, 1, 10),
(13, 'Telaraña', 'BICHO', 'ATRAPADO', 5, 1, 5),
(16, 'Picadura', 'BICHO', 'ENVENENADO', 5, 1, 15),
(17, 'Picadura profunda','BICHO', 'GRAV_ENVENENADO', 3, 1, 5),
(27, 'Onda trueno', 'ELECTRICO', 'PARALIZADO', 3, 1, 10),
(34, 'Rayo confuso', 'FANTASMA', 'CONFUSION', 5, 1, 10),
(35, 'Rezo maldito', 'FANTASMA', 'MALDICION', 5, 1, 5),
(36, 'Ascuas', 'FUEGO', 'QUEMADURA', 5, 1, 10),
(37, 'Girofuego', 'FUEGO', 'ATRAPADO', 5, 1, 5),
(47, 'Rayo hielo', 'HIELO', 'CONGELAR', 5, 1, 10),
(52, 'Marcar músculo', 'LUCHA', 'AMEDENTRADO', 5, 1, 15),
(58, 'Atadura', 'NORMAL', 'ATRAPADO', 5, 1, 5),
(60, 'Beso amoroso', 'NORMAL', 'ENAMORADO', 5, 1, 10),
(62, 'Chirrido', 'NORMAL', 'CONFUSION', 5, 1, 5),
(71, 'Drenadoras', 'PLANTA', 'DRENADORAS', 5, 1, 10),
(72, 'Esporas', 'PLANTA', 'DORMIDO', 3, 1, 5),
(74, 'Paralizador', 'PLANTA', 'PARALIZADO', 3, 1, 10),
(76, 'Somnífero', 'PLANTA', 'SOMNOLIENTO', 3, 1, 15),
(95, 'Gas venenoso', 'VENENO', 'ENVENENADO', 5, 1, 30),
(97, 'Tóxico', 'VENENO', 'GRAV_ENVENENADO', 5, 1, 20);


INSERT INTO MOVIMIENTOS (ID_MOVIMIENTO, NOM_MOVIMIENTO, TIPO, POTENCIA, TIPO_DAÑO, NIVEL_APRENDIZAJE, PP_MAX) VALUES
(1, 'Placaje', 'NORMAL', 40, 'FISICO', 1, 35),
(2, 'Hiperrayo', 'NORMAL',	150, 'ESPECIAL', 1, 5),
(5, 'Burbuja', 'AGUA', 40, 'ESPECIAL', 1, 30),
(6, 'Cascada', 'AGUA', 80, 'FISICO', 1, 15),
(7, 'Hidrobomba', 'AGUA', 110, 'ESPECIAL', 1, 5),
(8, 'Martillazo', 'AGUA', 110, 'FISICO', 1, 10),
(9, 'Pistola agua', 'AGUA', 80, 'ESPECIAL', 1, 20),
(14, 'Corte furia', 'BICHO', 40, 'FISICO', 1, 30),
(15, 'Megacuerno', 'BICHO', 120, 'FISICO', 1, 5),
(19, 'Rayo señal', 'BICHO', 75, 'ESPECIAL', 1, 15),
(20, 'Pin misil', 'BICHO', 35, 'ESPECIAL', 1, 30),
(21, 'Furia dragon', 'DRAGON', 60, 'ESPECIAL', 1, 15),
(22, 'Dragoaliento', 'DRAGON', 150, 'ESPECIAL', 1, 5),
(23, 'Garra dragón', 'DRAGON', 60, 'FISICO', 1, 10),
(26, 'Impactrueno', 'ELECTRICO', 40, 'ESPECIAL', 1, 30),
(28, 'Puño trueno', 'ELECTRICO', 75, 'FISICO', 1, 20),
(29, 'Rayo', 'ELECTRICO', 90, 'ESPECIAL', 1, 15),
(30, 'Trueno', 'ELECTRICO', 120, 'ESPECIAL', 1, 10),
(32, 'Lengüetazo,', 'FANTASMA', 30, 'FISICO', 1, 30),
(33, 'Tinieblas', 'FANTASMA', 100, 'ESPECIAL', 1, 20),
(38, 'Lanzallamas', 'FUEGO', 90, 'ESPECIAL', 1, 15),
(39, 'Llamarada', 'FUEGO', 110, 'ESPECIAL', 1, 5),
(40, 'Puño fuego', 'FUEGO', 80, 'FISICO', 1, 20),
(45, 'Puño hielo', 'HIELO', 75, 'FISICO', 1, 20),
(46, 'Rayo aurora', 'HIELO', 65, 'ESPECIAL', 1, 20),
(48, 'Ventisca', 'HIELO', 130, 'ESPECIAL', 1, 5),
(49, 'Golpe kárate', 'LUCHA', 50, 'FISICO', 1, 30),
(50, 'Patada giratoria', 'LUCHA', 70, 'FISICO', 1, 20),
(51, 'Esfera aural', 'LUCHA', 200, 'ESPECIAL', 1, 5),
(57, 'Arañazo', 'NORMAL', 40, 'FISICO', 1, 35),
(59, 'Atizar', 'NORMAL', 65, 'FISICO', 1, 25),
(61, 'Bomba sónica','NORMAL', 70, 'ESPECIAL', 1, 25),
(63, 'Cornada','NORMAL', 65, 'FISICO', 1, 25),
(64, 'Cuchillada', 'NORMAL', 75, 'FISICO', 1, 20),
(67, 'Fuerza', 'NORMAL', 80, 'FISICO', 1, 20),
(68, 'Meteoros', 'NORMAL', 100, 'ESPECIAL', 1, 15),
(69, 'Absorber', 'PLANTA', 30, 'ESPECIAL', 1, 25),
(70, 'Danza pétalo', 'PLANTA', 120, 'ESPECIAL', 1, 10),
(73, 'Hoja afilada', 'PLANTA', 55, 'FISICO', 1, 25),
(75, 'Rayo solar', 'PLANTA', 120, 'ESPECIAL', 1, 10),
(81, 'Psicoonda', 'PSIQUICO', 40, 'ESPECIAL', 1, 30),
(82, 'Psicorayo', 'PSIQUICO', 65, 'ESPECIAL', 1, 20),
(83, 'Psíquico', 'PSIQUICO', 90, 'ESPECIAL', 1, 15),
(84, 'Golpe kinético', 'PSIQUICO', 150, 'FISICO', 1, 5),
(85, 'Avalancha', 'ROCA', 75, 'FISICO', 1, 10),
(86, 'Lanzarrocas', 'ROCA', 50, 'FISICO', 1, 20),
(87, 'Poder pasado', 'ROCA', 65, 'ESPECIAL', 1, 10),
(89, 'Hueso palo', 'TIERRA', 65, ' FISICO', 1, 20),
(90, 'Huesomerang', 'TIERRA', 85, 'FISICO', 1, 15),
(91, 'Terremoto', 'TIERRA', 120, 'FISICO', 1, 5),
(93, 'Ácido', 'VENENO', 40, 'ESPECIAL', 1, 30),
(96, 'Picotazo venenoso', 'VENENO', 20, 'FISICO', 1, 50),
(98, 'Ataque aéreo', 'VOLADOR', 140, 'FISICO', 1, 5),
(100, 'Ataque ala', 'VOLADOR', 60, 'FISICO', 1, 20),
(102, 'Picotazo', 'VOLADOR', 35, 'FISICO', 1 , 40),
(103, 'Tornado', 'VOLADOR', 45, 'ESPECIAL', 1, 30),
(104, 'Huracan', 'VOLADOR', 100, 'ESPECIAL', 1, 15);


---EL SIGUIENTE INSERT SOLO SIRVE PARA DARLE MOVIMIENTOS A LOS POKEMONS DESDE LA BBDD
---SUSTITUIR LAS ? POR LA ID DEL POKEMON EN CUESTIÓN.
---SI EL POKEMON TIENE UA CUATRO MOVIMIENTOS EN 1, CAMBIAR POR 0 EN EL INSERT.
INSERT INTO MOVIMIENTOS POKEMON (ID_MOVIMIENTO, ID_POKEMON, ACTIVO, PP_REST)
SELECT M.ID_MOVIMIENTO, P.ID_POKEMON, 1, M.PP_MAX
FROM MOVIMIENTOS M, POKEMON P, POKEDEX PX
WHERE M.NIVEL_APRENDIZAJE <= 1
AND P.ID_POKEMON = ?
AND P.NUM_POKEDEX = PX.NUM_POKEDEX
AND (M.TIPO = 'NORMAL' OR M.TIPO = PX.TIPO1 OR M.TIPO = PX.TIPO2)
AND NOT EXISTS (
    SELECT *
    FROM MOVIMIENTOS_POKEMON MP
    WHERE MP.ID_MOVIMIENTO = M.ID_MOVIMIENTO AND
    MP.ID_POKEMON = ?
)
ORDER BY RAND() LIMIT 1;


--INSERT DE POKEMONS PARA PRUEBAS
INSERT INTO POKEMON (ID_POKEMON, NUM_POKEDEX, ID_ENTRENADOR, MOTE, CAJA, ATAQUE, AT_ESPECIAL, DEFENSA, DEF_ESPECIAL, VELOCIDAD, VITALIDAD, VIT_MAX, NIVEL, EXPERIENCIA, SEXO, FERTILIDAD, ESTADO) VALUES
(1, 26, 1, 'El Roto', 1, 80, 80, 80, 80, 80, 800, 800, 11, 119, 'H', 5, 'Saludable');

INSERT INTO `movimientos_pokemon`(`ID_MOVIMIENTO`, `ID_POKEMON`, `ACTIVO`, `PP_REST`) VALUES
(30, 999, 1,(SELECT PP_MAX FROM movimientos WHERE ID_MOVIMIENTO = 30)),
(31, 999, 1,(SELECT PP_MAX FROM movimientos WHERE ID_MOVIMIENTO = 31)),
(2, 999, 1,(SELECT PP_MAX FROM movimientos WHERE ID_MOVIMIENTO = 2)),
(28, 999, 1,(SELECT PP_MAX FROM movimientos WHERE ID_MOVIMIENTO = 28));

