package es.cesur.progprojectpok.clases;

public class MovimientoEstado extends Movimiento{
    //Estudiar el cambiarlo a un Enum
    String estado;
    int duracionTurnos;

    public MovimientoEstado(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoEstado(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, String estado, int duracionTurnos) {
        super(nombre, ppMax, ppRest, tipo, prioridad);
        this.estado = estado;
        this.duracionTurnos = duracionTurnos;
    }

    public void aplicarEstado(Pokemon pokemon){

    }


}
