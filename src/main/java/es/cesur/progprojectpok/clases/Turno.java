package es.cesur.progprojectpok.clases;

public class Turno {

    private int numeroCombate;
    private int numeroTurno = 0;
    private String accionJugador = null;
    private String accionRival = null;

    public Turno(int numeroCombate, int numeroTurno, String accionJugador, String accionRival) {
        this.numeroCombate = numeroCombate;
        this.numeroTurno = numeroTurno;
        this.accionJugador = accionJugador;
        this.accionRival = accionRival;
    }

    public void crearLog(){
        //Lógica de crear log.
    }

}
