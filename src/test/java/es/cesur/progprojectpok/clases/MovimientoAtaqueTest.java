package es.cesur.progprojectpok.clases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovimientoAtaqueTest {

    @Test
    void testDamageCalcStab() {
        MovimientoAtaque movimientoAtaque = new MovimientoAtaque("MovimientoTest1", 1, 1, Tipos.NORMAL, 100, "FISICO");

        Pokemon pokemon = new Pokemon("PokemonTest1", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.NULL);
        int ataque = pokemon.getAtaque();
        int defensa = 30;
        Tipos tipo1Objetivo = Tipos.NORMAL;
        Tipos tipo2Objetivo = Tipos.NULL;

        // Llamada al método
        int damage = movimientoAtaque.damageCalc(pokemon, ataque, defensa, tipo1Objetivo, tipo2Objetivo);

        // Validación de resultados
        assertTrue(damage >= 28 && damage <= 33);
    }

    @Test
    void testDamageCalcFantasma() {
        MovimientoAtaque movimientoAtaque = new MovimientoAtaque("MovimientoTest2", 1, 1, Tipos.FANTASMA, 100, "FISICO");

        Pokemon pokemon = new Pokemon("PokemonTest2", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.NULL);
        int ataque = 30;
        int defensa = 30;
        Tipos tipo1Objetivo = Tipos.NORMAL;
        Tipos tipo2Objetivo = Tipos.NULL;

        // Llamada al método
        int damage = movimientoAtaque.damageCalc(pokemon, ataque, defensa, tipo1Objetivo, tipo2Objetivo);

        // Validación de resultados
        assertEquals(0, damage);
    }

    @Test
    void testDamageCalcVentajas() {
        MovimientoAtaque movimientoAtaque = new MovimientoAtaque("MovimientoTest3", 1, 1, Tipos.FUEGO, 100, "FISICO");

        Pokemon pokemon = new Pokemon("PokemonTest3", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.FUEGO);
        int ataque = 30;
        int defensa = 30;
        Tipos tipo1Objetivo = Tipos.PLANTA;
        Tipos tipo2Objetivo = Tipos.BICHO;

        // Llamada al método
        int damage = movimientoAtaque.damageCalc(pokemon, ataque, defensa, tipo1Objetivo, tipo2Objetivo);

        // Validación de resultados
        assertTrue(damage >= 112 && damage <= 132);
    }

    @Test
    void testDamageCalcVentyDesv() {
        MovimientoAtaque movimientoAtaque = new MovimientoAtaque("MovimientoTest4", 1, 1, Tipos.FUEGO, 100, "FISICO");

        Pokemon pokemon = new Pokemon("PokemonTest4", 100, 100, 30, 45, 30, 45, 20, 20, Tipos.NORMAL, Tipos.FUEGO);
        int ataque = 30;
        int defensa = 30;
        Tipos tipo1Objetivo = Tipos.ROCA;
        Tipos tipo2Objetivo = Tipos.BICHO;

        // Llamada al método
        int damage = movimientoAtaque.damageCalc(pokemon, ataque, defensa, tipo1Objetivo, tipo2Objetivo);

        // Validación de resultados
        assertTrue(damage >= 28.05 && damage <= 33);
    }

}