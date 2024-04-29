package es.cesur.progprojectpok.clases;

public class Turno {

    private int numeroCombate;
    private int numeroTurno;
    private String accionJugador;
    private String accionRival;

    public Turno(int numeroCombate, int numeroTurno, String accionJugador, String accionRival) {
        this.numeroCombate = numeroCombate;
        this.numeroTurno = numeroTurno;
        this.accionJugador = accionJugador;
        this.accionRival = accionRival;
    }

}
