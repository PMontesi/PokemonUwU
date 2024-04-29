package es.cesur.progprojectpok.clases;

public class MovimientoAtaque extends Movimiento{
    int potencia;

    public MovimientoAtaque(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoAtaque(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, int potencia) {
        super(nombre, ppMax, ppRest, tipo, prioridad);
        this.potencia = potencia;
    }
    public void aplicarDamage(Pokemon pokemon){

    }


}
