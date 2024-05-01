package es.cesur.progprojectpok.clases;

public class MovimientoAtaque extends Movimiento{
    int potencia;
    String tipoAtaque;

    public MovimientoAtaque(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoAtaque(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, int potencia, String tipoAtaque, int idMovimiento) {
        super(nombre, ppMax, ppRest, tipo, prioridad, idMovimiento);
        this.potencia = potencia;
        this.tipoAtaque = tipoAtaque;
    }

    //Pedir AT/AT_ESP como argumentos
    public void aplicarDamage(Pokemon pokemonObjetivo, int estadistica){
        if(getTipoAtaque().toUpperCase().equals("FISICO")){
            if((pokemonObjetivo.getVitalidad()) - estadistica/pokemonObjetivo.getDefensa() <= 0){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) - estadistica/pokemonObjetivo.getDefensa());

            System.out.println("Daño: " + estadistica/pokemonObjetivo.getDefensa() + "\n ------------------------------");
        } else if (getTipoAtaque().toUpperCase().equals("ESPECIAL")) {
            if ((pokemonObjetivo.getVitalidad()) - estadistica/pokemonObjetivo.getDefensaEspecial() <= 0){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) - estadistica/pokemonObjetivo.getDefensaEspecial());

            System.out.println("Daño: " + estadistica/pokemonObjetivo.getDefensaEspecial() + "\n ------------------------------");
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

    @Override
    public String toString() {
        return super.toString() + "MovimientoAtaque{" +
                "potencia=" + potencia +
                ", tipoAtaque='" + tipoAtaque + '\'' +
                '}';
    }
}
