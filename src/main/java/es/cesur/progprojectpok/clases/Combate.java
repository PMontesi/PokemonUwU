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
            if(checkStun(pokemonA)){
                System.out.println("NO PUEDE ATACAR");
            }
            else if(checkSelfCast(pokemonA, movimientoA)){
                pokemonA.usarMovimiento(movimientoA, pokemonA, pokemonA);
            }
            else pokemonA.usarMovimiento(movimientoA, pokemonB, pokemonA);

            if(checkStun(pokemonB)){
                System.out.println("NO PUEDE ATACAR");
            }
            else if(checkSelfCast(pokemonB, movimientoB)){
                pokemonB.usarMovimiento(movimientoB, pokemonB, pokemonB);
            }
            else pokemonB.usarMovimiento(movimientoB, pokemonA, pokemonB);

            setPrimerPoke(pokemonA);
            setMovimientoUsadoPrimer(movimientoA);
            setSegundoPoke(pokemonB);
            setMovimientoUsadoSegund(movimientoB);
        }
        else if (pokemonA.getPrioridad() < pokemonB.getPrioridad()) {
            if(checkStun(pokemonA)){
                System.out.println("NO PUEDE ATACAR");
            }
            else if(checkSelfCast(pokemonB, movimientoB)){
                pokemonB.usarMovimiento(movimientoB, pokemonB, pokemonB);
            }
            else pokemonB.usarMovimiento(movimientoB, pokemonA, pokemonB);

            if(checkStun(pokemonB)){
                System.out.println("NO PUEDE ATACAR");
            }
            else if(checkSelfCast(pokemonA, movimientoA)){
                pokemonA.usarMovimiento(movimientoA, pokemonA, pokemonB);
            }
            else pokemonA.usarMovimiento(movimientoA, pokemonB, pokemonB);

            setPrimerPoke(pokemonB);
            setMovimientoUsadoPrimer(movimientoB);
            setSegundoPoke(pokemonA);
            setMovimientoUsadoSegund(movimientoA);
        }
    }


    public void setPrioridad (Pokemon pokemonA, Pokemon pokemonB){
        int velPokeA = pokemonA.getVelocidad();
        if (pokemonA.getEstadosPersistentes() == EstPersitentesEnum.PARALIZADO
            || pokemonA.getEstadosPersistentes() == EstPersitentesEnum.DORMIDO){
            velPokeA *= 0.5;
        }
        if (velPokeA <= 1) velPokeA = 1;

        int velPokeB = pokemonB.getVelocidad();
        if (pokemonB.getEstadosPersistentes() == EstPersitentesEnum.PARALIZADO
            || pokemonB.getEstadosPersistentes() == EstPersitentesEnum.DORMIDO) {
            velPokeB *= 0.5;
        }
        if (velPokeB <= 1) velPokeB = 1;

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
        else if (pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.CONFUSO) && (((int) (Math.random() * 3 + 1) == 1))) return true;
        //AMPLIAR CON MÁS POSIBLES AUTOESTADOS
        return false;
    }

    public boolean checkStun(Pokemon pokemon){
        if (    pokemon.getEstadosPersistentes() != null
                || pokemon.getEstTemporalesEnums() != null
                || pokemon.getEstadosPersistentes() == EstPersitentesEnum.DORMIDO
                || pokemon.getEstadosPersistentes() == EstPersitentesEnum.CONGELADO
                || (pokemon.getEstadosPersistentes() == EstPersitentesEnum.PARALIZADO && (((int) (Math.random() * 4 + 1) != 1)))
                || (pokemon.getEstadosPersistentes() == EstPersitentesEnum.SOMNOLIENTO && (((int) (Math.random() * 2 + 1) != 1)))
                || (pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.ENAMORADO) && (((int) (Math.random() * 2 + 1) != 1)))
                || pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.AMEDRENTADO))
        {
            return true;
        }


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
