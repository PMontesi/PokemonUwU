package es.cesur.progprojectpok.clases;

public class Movimiento {
    private String nombre;
    private int idMovimiento;
    private int ppMax;
    private int ppRest;
    private Tipos tipo;
    private int prioridad;

    public Movimiento() {
    }

    public Movimiento(String nombre, int idMovimiento) {
        this.nombre = nombre;
        this.idMovimiento = idMovimiento;
    }

    public Movimiento(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, int idMovimiento) {
        this.nombre = nombre;
        this.ppMax = ppMax;
        this.ppRest = ppRest;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.idMovimiento = idMovimiento;
    }

    //Constructor para test
    public Movimiento(String nombre, int ppMax, int ppRest, Tipos tipo) {
        this.nombre = nombre;
        this.ppMax = ppMax;
        this.ppRest = ppRest;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getPpMax() {
        return ppMax;
    }

    public void setPpMax(int ppMax) {
        this.ppMax = ppMax;
    }

    public int getPpRest() {
        return ppRest;
    }

    public void setPpRest(int ppRest) {
        this.ppRest = ppRest;
    }

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "nombre='" + nombre + '\'' +
                ", idMovimiento=" + idMovimiento +
                ", ppMax=" + ppMax +
                ", ppRest=" + ppRest +
                ", tipo=" + tipo +
                ", prioridad=" + prioridad +
                '}';
    }
}
