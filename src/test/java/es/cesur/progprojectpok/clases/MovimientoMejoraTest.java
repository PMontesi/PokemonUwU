package es.cesur.progprojectpok.clases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MejoraAplicaTest {

    @Test
    void testMejoraAplicaAtaque() {
        MovimientoMejora movimiento = new MovimientoMejora("MovimientoTest1", 1, 1, Tipos.NORMAL, "ATAQUE", 100);

        Pokemon pokemon = new Pokemon("PokemonTest1", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.NULL);
        int cantidadMejora = movimiento.getCantidadMejora();


        int ataqueOriginal = pokemon.getAtaque();

        // Llamada al método
        movimiento.mejoraAplica(pokemon);

        int ataqueEsperado = (int) (ataqueOriginal * (1 + cantidadMejora / 100));

        assertEquals(60, ataqueEsperado);
    }

    @Test
    void testMejoraAplicaDefensa() {
        MovimientoMejora movimiento = new MovimientoMejora("MovimientoTest2", 1, 1, Tipos.NORMAL, "DEFENSA", 100);

        Pokemon pokemon = new Pokemon("PokemonTest1", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.NULL);
        int cantidadMejora = movimiento.getCantidadMejora();


        int defensaOriginal = pokemon.getDefensa();

        // Llamada al método
        movimiento.mejoraAplica(pokemon);

        int defensaEsperada = (int) (defensaOriginal * (1 + cantidadMejora / 100));

        assertEquals(90, defensaEsperada);
    }

    @Test
    void testMejoraAplicaAtEspecial() {
        MovimientoMejora movimiento = new MovimientoMejora("MovimientoTest3", 1, 1, Tipos.NORMAL, "AT_ESP", 100);

        Pokemon pokemon = new Pokemon("PokemonTest1", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.NULL);
        int cantidadMejora = movimiento.getCantidadMejora();


        int atEspOriginal = pokemon.getAtaqueEspecial();

        // Llamada al método
        movimiento.mejoraAplica(pokemon);

        int atEspEsperado = (int) (atEspOriginal * (1 + cantidadMejora / 100));

        assertEquals(60, atEspEsperado);
    }

    @Test
    void testMejoraAplicaDefEspecial() {
        MovimientoMejora movimiento = new MovimientoMejora("MovimientoTest4", 1, 1, Tipos.NORMAL, "DEF_ESP", 100);

        Pokemon pokemon = new Pokemon("PokemonTest1", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.NULL);
        int cantidadMejora = movimiento.getCantidadMejora();


        int defEspOriginal = pokemon.getDefensaEspecial();

        // Llamada al método
        movimiento.mejoraAplica(pokemon);

        int defEspEsperada = (int) (defEspOriginal * (1 + cantidadMejora / 100));

        assertEquals(90, defEspEsperada);
    }

    @Test
    void testMejoraAplicaVelocidadl() {
        MovimientoMejora movimiento = new MovimientoMejora("MovimientoTest5", 1, 1, Tipos.NORMAL, "VELOCIDAD", 100);

        Pokemon pokemon = new Pokemon("PokemonTest1", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.NULL);
        int cantidadMejora = movimiento.getCantidadMejora();


        int velocidadOriginal = pokemon.getVelocidad();

        // Llamada al método
        movimiento.mejoraAplica(pokemon);

        int velocidadEsperada = (int) (velocidadOriginal * (1 + cantidadMejora / 100));

        assertEquals(40, velocidadEsperada);
    }

}
