/* IMPORTANTE: Borrar la clave foránea antes de hacer los inserts. Phpmyadmin no admite DISABLE/ENABLE.
Otra opción es insertar primero los pokemons que no evolucionan e ir insertando las prevoluciones, es decir,
insertar primero a Venusaur, después Ivysaur y por último Bulbasaur.
*/
ALTER TABLE POKEDEX DROP INDEX NUM_POKEDEX_EVO_FK; 

INSERT INTO POKEDEX (NUM_POKEDEX, NOM_POKEMON, TIPO1, TIPO2, IMAGEN_DELANTE, IMAGEN_DETRAS, IMAGEN_DELANTE_F, IMAGEN_DETRAS_F, SONIDO, NIVEL_EVOLUCION, NUM_POKEDEX_EVO) VALUES
(1, 'Bulbasaur', 'Planta', 'Veneno', 	'@../images/pokemons/bulbasaur-front.png', 		'@../images/pokemons/bulbasur-back.png', NULL, NULL, 'sonido1.mp3', 16, 2),
(2, 'Ivysaur', 'Planta', 'Veneno', 		'@../images/pokemons/ivysaur-front.png', 		'@../images/pokemons/ivysaur-back.png', NULL, NULL, 'sonido2.mp3', 32, 3),
(3, 'Venusaur', 'Planta', 'Veneno', 	'@../images/pokemons/venusaur-m-front.png', 	'@../images/pokemons/venusaur-m-back.png', '@../images/pokemons/venusaur-f-front.png', '@../images/pokemons/venusaur-f-back.png', 'sonido3.mp3', NULL, NULL),
(4, 'Charmander', 'Fuego', NULL, 		'@../images/pokemons/charmander-front.png', 	'@../images/pokemons/charmander-back.png', NULL, NULL, 'sonido4.mp3', 16, 5),
(5, 'Charmeleon', 'Fuego', NULL, 		'@../images/pokemons/charmeleon-front.png', 	'@../images/pokemons/charmeleon-back.png', NULL, NULL, 'sonido5.mp3', 36, 6),
(6, 'Charizard', 'Fuego', 'Volador', 	'@../images/pokemons/charizard-front.png', 		'@../images/pokemons/charizard-back.png', NULL, NULL, 'sonido6.mp3', NULL, NULL),
(7, 'Squirtle', 'Agua', NULL, 			'@../images/pokemons/squirtle-front.png', 		'@../images/pokemons/squirtle-back.png', NULL, NULL, 'sonido7.mp3', 16, 8),
(8, 'Wartortle', 'Agua', NULL, 			'@../images/pokemons/wartortle-front.png', 		'@../images/pokemons/wartortle-back.png', NULL, NULL, 'sonido8.mp3', 36, 9),
(9, 'Blastoise', 'Agua', NULL, 			'@../images/pokemons/blastoise-front.png', 		'@../images/pokemons/blastoise-back.png', NULL, NULL, 'sonido9.mp3', NULL, NULL),
(10, 'Caterpie', 'Bicho', NULL, 		'@../images/pokemons/caterpie-front.png', 		'@../images/pokemons/caterpie-back.png', NULL, NULL, 'sonido10.mp3', 7, 11),
(11, 'Metapod', 'Bicho', NULL, 			'@../images/pokemons/metapod-front.png', 		'@../images/pokemons/metapod-back.png', NULL, NULL, 'sonido11.mp3', 10, 12),
(12, 'Butterfree', 'Bicho', 'Volador', 	'@../images/pokemons/butterfree-m-front.png', 	'@../images/pokemons/butterfree-m-back.png', '@../images/pokemons/butterfree-f-front.png', '@../images/pokemons/butterfree-f-back.png', 'sonido12.mp3', NULL, NULL),
(13, 'Weedle', 'Bicho', 'Veneno', 		'@../images/pokemons/weedle-front.png', 		'@../images/pokemons/weedle-back.png', NULL, NULL, 'sonido13.mp3', 7, 14),
(14, 'Kakuna', 'Bicho', 'Veneno', 		'@../images/pokemons/kakuna-front.png', 		'@../images/pokemons/kakuna-back.png', NULL, NULL, 'sonido14.mp3', 10, 15),
(15, 'Beedrill', 'Bicho', 'Veneno', 	'@../images/pokemons/beedrill-front.png', 		'@../images/pokemons/beedrill-back.png', NULL, NULL, 'sonido15.mp3', NULL, NULL),
(16, 'Pidgey', 'Normal', 'Volador', 	'@../images/pokemons/pidgey-front.png', 		'@../images/pokemons/pidgey-back.png', NULL, NULL, 'sonido16.mp3', 18, 17),
(17, 'Pidgeotto', 'Normal', 'Volador', 	'@../images/pokemons/pidgeotto-front.png', 		'@../images/pokemons/pidgeotto-back.png', NULL, NULL, 'sonido17.mp3', 36, 18),
(18, 'Pidgeot', 'Normal', 'Volador', 	'@../images/pokemons/pidgeot-front.png', 		'@../images/pokemons/pidgeot-back.png', NULL, NULL, 'sonido18.mp3', NULL, NULL),
(19, 'Rattata', 'Normal', NULL, 		'@../images/pokemons/rattata-m-front.png', 		'@../images/pokemons/rattata-m-back.png', '@../images/pokemons/rattata-f-front.png', '@../images/pokemons/rattata-f-back.png', 'sonido19.mp3', 20, 20),
(20, 'Raticate', 'Normal', NULL, 		'@../images/pokemons/raticate-m-front.png', 	'@../images/pokemons/raticate-m-back.png', '@../images/pokemons/raticate-f-front.png', '@../images/pokemons/raticate-f-back.png', 'sonido20.mp3', NULL, NULL),
(21, 'Spearow', 'Normal', 'Volador', 	'@../images/pokemons/spearow-front.png', 		'@../images/pokemons/spearow-back.png', NULL, NULL, 'sonido21.mp3', 20, 22),
(22, 'Fearow', 'Normal', 'Volador', 	'@../images/pokemons/fearow-front.png', 		'@../images/pokemons/fearow-back.png', NULL, NULL, 'sonido22.mp3', NULL, NULL),
(23, 'Ekans', 'Veneno', NULL, 			'@../images/pokemons/ekans-front.png', 			'@../images/pokemons/ekans-back.png', NULL, NULL, 'sonido23.mp3', 22, 24),
(24, 'Arbok', 'Veneno', NULL, 			'@../images/pokemons/arbok-front.png', 			'@../images/pokemons/arbok-back.png', NULL, NULL, 'sonido24.mp3', NULL, NULL),
(25, 'Pikachu', 'Eléctrico', NULL, 		'@../images/pokemons/pikachu-m-front.png', 		'@../images/pokemons/pikachu-m-back.png', '@../images/pokemons/pikachu-f-front.png', '@../images/pokemons/pikachu-f-back.png', 'sonido25.mp3', 28, 26),
(26, 'Raichu', 'Eléctrico', NULL, 		'@../images/pokemons/raichu-m-front.png', 		'@../images/pokemons/raichu-m-back.png', '@../images/pokemons/raichu-f-front.png', '@../images/pokemons/raichu-f-back.png', 'sonido26.mp3', NULL, NULL);

-- IMPORTANTE: Añadir de nuevo al clave foránea al terminar los inserts si se ha eliminado.
ALTER TABLE POKEDEX
ADD CONSTRAINT NUM_POKEDEX_EVO_FK 
	FOREIGN KEY (NUM_POKEDEX_EVO) 
	REFERENCES POKEDEX(NUM_POKEDEX);


INSERT INTO ENTRENADOR (ID_ENTRENADOR, NOM_ENTRENADOR, PASS, POKEDOLLARS) VALUES (1, 'Paco', 123456, 999999);