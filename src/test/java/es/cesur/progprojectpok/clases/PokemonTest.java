package es.cesur.progprojectpok.clases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void testRandomSex() {
        Pokemon pokemon = new Pokemon();
        char sexo = pokemon.randomSex();

        // Validación de resultados
        assertTrue(sexo == 'H' || sexo == 'M', "El sexo generado no es ni 'H' ni 'M'");
    }


    @Test
    void testSubidaEstadisticasInstananea() {
        Pokemon pokemon = new Pokemon();

        int nivel = 10;

        int estadistica = pokemon.subidaEstadisticasInstananea(nivel);

        int estadisticaMinimaEsperada = 0;
        int estadisticaMaximaEsperada = 0;
        for (int i = 1; i < nivel; i++) {
            estadisticaMinimaEsperada += 1;
            estadisticaMaximaEsperada += 5;
        }
        assertTrue(estadistica >= estadisticaMinimaEsperada, "La estadística está por encima del mínimo esperado");
        assertTrue(estadistica <= estadisticaMaximaEsperada, "La estadística está por debajo del máximo esperado");
    }

    @Test
    void testNivelAleatorio() {
        Pokemon pokemon = new Pokemon();

        int nivel = pokemon.nivelAleatorio();


        assertTrue(nivel >= -3 && nivel <= 3, "El nivel está dentro del rango esperado");
    }
}
