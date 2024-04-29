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


    public int getNumeroCombate() {
        return numeroCombate;
    }

    public void setNumeroCombate(int numeroCombate) {
        this.numeroCombate = numeroCombate;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public String getAccionJugador() {
        return accionJugador;
    }

    public void setAccionJugador(String accionJugador) {
        this.accionJugador = accionJugador;
    }

    public String getAccionRival() {
        return accionRival;
    }

    public void setAccionRival(String accionRival) {
        this.accionRival = accionRival;
    }
}
