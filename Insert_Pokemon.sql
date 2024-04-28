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

INSERT INTO MOVIMIENTOS (ID_MOVIMIENTO, NOM_MOVIMIENTO, POTENCIA, TIPO, ESTADO, TIPO_DAÑO, TURNOS, MEJORA, CANT_MEJORA, NIVEL_APRENDIZAJE, PP_MAX, PP_REST) VALUES
						(1, 			'Placaje', 		40, 	'NORMAL', null, 'FISICO', null, null, null, 1, 35, 35),
						(2, 			'Hiperrayo', 	150, 	'NORMAL', null, 'ESPECIAL', null, null, null, 1, 5, 5),
						(3, 			'Danza espada', null, 	'NORMAL', null,	null,		5,	'ATAQUE', 100, 1, 20, 20),
                        (4, 			'Canto',		null,	'NORMAL', 'DORMIDO', null, 3, 	null, 	null, 1, 15, 15);
