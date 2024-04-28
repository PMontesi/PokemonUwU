package es.cesur.progprojectpok.clases;

public class MovimientoMejora extends Movimiento{
    private int duracionTurnos;
    private String estadistica;
    private int cantidadMejora;

    public MovimientoMejora(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoMejora(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, int duracionTurnos, String estadistica, int cantidadMejora) {
        super(nombre, ppMax, ppRest, tipo, prioridad);
        this.duracionTurnos = duracionTurnos;
        this.estadistica = estadistica;
        this.cantidadMejora = cantidadMejora;
    }

    //Esto quizá haya que hacerlo por cada movimiento.
    public void mejoraAplica(Pokemon pokemonObjetivo){

    }

}
