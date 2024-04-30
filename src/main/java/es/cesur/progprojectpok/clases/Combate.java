package es.cesur.progprojectpok.clases;

import java.util.ArrayList;

public class Combate {
    private ArrayList<Turno> turnos;
    private Entrenador usuario;
    private Entrenador rival;
    private Entrenador ganador = null;
    private int koJugador = 0;
    private int koRival = 0;

    public Combate(Entrenador usuario, Entrenador rival) {
        this.turnos = new ArrayList<>();
        this.usuario = usuario;
        this.rival = rival;
    }

    public void combatir (Pokemon pokemonA, int movimientoA, Pokemon pokemonB, int movimientoB){
        if (pokemonA.getVelocidad() > pokemonB.getVelocidad()){
            pokemonA.usarMovimiento(movimientoA, pokemonB);
            pokemonB.usarMovimiento(movimientoB, pokemonA);
        }
        else if (pokemonB.getVelocidad() > pokemonA.getVelocidad()) {
            pokemonB.usarMovimiento(movimientoB, pokemonA);
            pokemonA.usarMovimiento(movimientoA, pokemonB);
        }
        else if (pokemonA.getVelocidad() == pokemonB.getVelocidad()){
            int random = (int) (Math.random()*2);
            if (random == 0){
                pokemonA.usarMovimiento(movimientoA, pokemonB);
                pokemonB.usarMovimiento(movimientoB, pokemonA);
            } else if ( random == 1) {
                pokemonB.usarMovimiento(movimientoB, pokemonA);
                pokemonA.usarMovimiento(movimientoA, pokemonB);
            }
        }
    }

    public void comprobarEstado(Pokemon pokemon){

    }
    public void retirarse(Entrenador usuario){}
    public void determinarGanador(){}
    public void entregarPokedolares(Entrenador entrenador){}
    public void recibirExperiencia(Pokemon pokemon){}

    public void crearLog(){
        //Lógica de crear log.
    }

    //GETTERS Y SETTERS
    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = turnos;
    }

    public Entrenador getUsuario() {
        return usuario;
    }

    public void setUsuario(Entrenador usuarior) {
        this.usuario = usuario;
    }

    public Entrenador getRival() {
        return rival;
    }

    public void setRival(Entrenador rival) {
        this.rival = rival;
    }

    public Entrenador getGanador() {
        return ganador;
    }

    public void setGanador(Entrenador ganador) {
        this.ganador = ganador;
    }

    public int getKoJugador() {
        return koJugador;
    }

    public void setKoJugador(int koJugador) {
        this.koJugador = koJugador;
    }

    public int getKoRival() {
        return koRival;
    }

    public void setKoRival(int koRival) {
        this.koRival = koRival;
    }
}
