package es.cesur.progprojectpok.clases;

public class MovimientoMejora extends Movimiento{
    private int duracionTurnos;
    private String estadistica;
    private int cantidadMejora;

    public MovimientoMejora(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoMejora(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, int duracionTurnos, String estadistica, int cantidadMejora, int idMovimiento) {
        super(nombre, ppMax, ppRest, tipo, prioridad, idMovimiento);
        this.duracionTurnos = duracionTurnos;
        this.estadistica = estadistica;
        this.cantidadMejora = cantidadMejora;
    }

    //Esto quizá haya que hacerlo por cada movimiento.
    public void mejoraAplica(Pokemon pokemonObjetivo){
        switch (getEstadistica()){
            case "ATAQUE" -> pokemonObjetivo.setAtaque(pokemonObjetivo.getAtaque()*(1+getCantidadMejora()/100));
            case "DEFENSA" -> pokemonObjetivo.setDefensa(pokemonObjetivo.getDefensa()*(1+getCantidadMejora()/100));
            case "AT_ESP" -> pokemonObjetivo.setAtaqueEspecial(pokemonObjetivo.getAtaqueEspecial()*(1+getCantidadMejora()/100));
            case "DEF_ESP" -> pokemonObjetivo.setDefensaEspecial(pokemonObjetivo.getDefensaEspecial()*(1+getCantidadMejora()/100));
            case "VELOCIDAD" -> pokemonObjetivo.setVelocidad(pokemonObjetivo.getVelocidad()*(1+getCantidadMejora()/100));
        }
    }

    public int getDuracionTurnos() {
        return duracionTurnos;
    }

    public void setDuracionTurnos(int duracionTurnos) {
        this.duracionTurnos = duracionTurnos;
    }

    public String getEstadistica() {
        return estadistica;
    }

    public void setEstadistica(String estadistica) {
        this.estadistica = estadistica;
    }

    public int getCantidadMejora() {
        return cantidadMejora;
    }

    public void setCantidadMejora(int cantidadMejora) {
        this.cantidadMejora = cantidadMejora;
    }

    @Override
    public String toString() {
        return super.toString() + "MovimientoMejora{" +
                "duracionTurnos=" + duracionTurnos +
                ", estadistica='" + estadistica + '\'' +
                ", cantidadMejora=" + cantidadMejora +
                '}';
    }
}
