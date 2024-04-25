package es.cesur.progprojectpok.clases;

import java.util.ArrayList;

public class Combate {
    private ArrayList<Turno> turnos;
    private Entrenador jugador;
    private Entrenador rival;
    private Entrenador ganador = null;
    private int koJugador = 0;
    private int koRival = 0;

    public Combate(Entrenador jugador, Entrenador rival) {
        this.turnos = new ArrayList<>();
        this.jugador = jugador;
        this.rival = rival;
    }

    public void combatir(Usuario jugador, Entrenador rival){

    }
    public void retirarse(Usuario jugador){}
    public void determinarGanador(){}
    public void entreharPokedolaros(){}
    public void recibirExperiencia(){}


}
