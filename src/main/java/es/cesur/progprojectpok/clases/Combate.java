package es.cesur.progprojectpok.clases;

import java.util.ArrayList;

public class Combate {
    private ArrayList<Turno> turnos;
    private Entrenador usuario;
    private Entrenador rival;
    private Entrenador ganador = null;
    private int koJugador = 0;
    private int koRival = 0;
    private Pokemon primerPoke;
    private Pokemon segundoPoke;
    private int movimientoUsadoPrimer;
    private int MovimientoUsadoSegund;

    public Combate(Entrenador usuario, Entrenador rival) {
        this.turnos = new ArrayList<>();
        this.usuario = usuario;
        this.rival = rival;
    }

    public void combatir (Pokemon pokemonA, int movimientoA, Pokemon pokemonB, int movimientoB){
        setPrioridad(pokemonA, pokemonB);
        if (pokemonA.getPrioridad() > pokemonB.getPrioridad()){
            if(checkSelfCast(pokemonA, movimientoA)){
                pokemonA.usarMovimiento(movimientoA, pokemonA);
            }
            else pokemonA.usarMovimiento(movimientoA, pokemonB);
            if(checkSelfCast(pokemonB, movimientoB)){
                pokemonB.usarMovimiento(movimientoB, pokemonB);
            }
            else pokemonB.usarMovimiento(movimientoB, pokemonA);

            setPrimerPoke(pokemonA);
            setMovimientoUsadoPrimer(movimientoA);
            setSegundoPoke(pokemonB);
            setMovimientoUsadoPrimer(movimientoB);
        }
        else if (pokemonA.getPrioridad() < pokemonB.getPrioridad()) {
            if(checkSelfCast(pokemonB, movimientoB)){
                pokemonB.usarMovimiento(movimientoB, pokemonB);
            }
            else pokemonB.usarMovimiento(movimientoB, pokemonA);
            if(checkSelfCast(pokemonA, movimientoA)){
                pokemonA.usarMovimiento(movimientoA, pokemonA);
            }
            else pokemonA.usarMovimiento(movimientoA, pokemonB);

            setPrimerPoke(pokemonB);
            setMovimientoUsadoPrimer(movimientoA);
            setSegundoPoke(pokemonA);
            setMovimientoUsadoPrimer(movimientoB);
        }
    }


    public void setPrioridad (Pokemon pokemonA, Pokemon pokemonB){
        if (pokemonA.getVelocidad() > pokemonB.getVelocidad()){
            pokemonA.setPrioridad(pokemonA.getPrioridad()+1);
        }
        else if (pokemonB.getVelocidad() > pokemonA.getVelocidad()) {
            pokemonB.setPrioridad(pokemonA.getPrioridad()+1);
        }
        else if (pokemonA.getVelocidad() == pokemonB.getVelocidad()){
            int random = (int) (Math.random()*2);
            if (random == 0){
                pokemonA.setPrioridad(pokemonA.getPrioridad()+1);
            }
            else if (random == 1) {
                pokemonB.setPrioridad(pokemonA.getPrioridad()+1);
            }
        }
    }

    public boolean checkSelfCast(Pokemon pokemon, int movimiento){
        if (pokemon.getMovimiento(movimiento) instanceof MovimientoMejora) return true;
        else if (pokemon.getEstadosTemporales() == EstadosTemporales.CONFUSO) return true;
        //AMPLIAR CON MÁS POSIBLES AUTOESTADOS
        return false;
    }

    //PROBABLMENTE HAYA QUE HACER UN CHECK PARA QUITAR OBJETIVOS O TURNOS O IO K SE

    /*
    public void comprobarEstado(Pokemon pokemon){
        if (pokemon.getEstadosPersistentes() == EstadosPersitentes.ENVENENADO){
            EstadoPersistente estadoPersistente = new EstadoPersistente(3);
            estadoPersistente.envenenar(pokemon);
        }

    }

     */
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

    public Pokemon getPrimerPoke() {
        return primerPoke;
    }

    public void setPrimerPoke(Pokemon primerPoke) {
        this.primerPoke = primerPoke;
    }

    public Pokemon getSegundoPoke() {
        return segundoPoke;
    }

    public void setSegundoPoke(Pokemon segundoPoke) {
        this.segundoPoke = segundoPoke;
    }

    public int getMovimientoUsadoPrimer() {
        return movimientoUsadoPrimer;
    }

    public void setMovimientoUsadoPrimer(int movimientoUsadoPrimer) {
        this.movimientoUsadoPrimer = movimientoUsadoPrimer;
    }

    public int getMovimientoUsadoSegund() {
        return MovimientoUsadoSegund;
    }

    public void setMovimientoUsadoSegund(int movimientoUsadoSegund) {
        MovimientoUsadoSegund = movimientoUsadoSegund;
    }
}
