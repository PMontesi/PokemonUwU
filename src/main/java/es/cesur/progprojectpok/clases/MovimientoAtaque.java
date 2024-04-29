package es.cesur.progprojectpok.clases;

public class MovimientoAtaque extends Movimiento{
    int potencia;
    String tipoAtaque;

    public MovimientoAtaque(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoAtaque(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, int potencia, String tipoAtaque) {
        super(nombre, ppMax, ppRest, tipo, prioridad);
        this.potencia = potencia;
        this.tipoAtaque = tipoAtaque;
    }

    //Pedir AT/AT_ESP como argumentos
    public void aplicarDamage(Pokemon pokemonObjetivo, int estadistica){
        if(getTipoAtaque().toUpperCase().equals("FISICO")){
            pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) - estadistica/pokemonObjetivo.getDefensa());
        } else if (getTipoAtaque().toUpperCase().equals("ESPECIAL")) {
            pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) - estadistica/pokemonObjetivo.getDefensaEspecial());
        }
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public String getTipoAtaque() {
        return tipoAtaque;
    }

    public void setTipoAtaque(String tipoAtaque) {
        this.tipoAtaque = tipoAtaque;
    }
}
