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
    public void aplicarDamage(Pokemon pokemonObjetivo, int estadistica, Pokemon pokemonCaster){
        int ataque = pokemonCaster.getAtaque();
        if (pokemonCaster.getEstadosPersistentes() == EstPersitentesEnum.QUEMADO)  ataque *= 0.5;
        if (ataque <= 0) ataque = 1;

        int ataqueEsp = pokemonCaster.getAtaqueEspecial();
        if (pokemonCaster.getEstadosPersistentes() == EstPersitentesEnum.HELADO) ataqueEsp *= 0.5;
        if (ataqueEsp <= 0) ataqueEsp = 1;

        float multEstado = 1f;
        if (pokemonObjetivo.getEstadosPersistentes() == EstPersitentesEnum.SOMNOLIENTO) multEstado += 0.5f;

        if(getTipoAtaque().toUpperCase().equals("FISICO")){

            if((damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa()) <= 0)){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((int) ((pokemonObjetivo.getVitalidad()) - (damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa())) * multEstado));

            System.out.println("Daño: " + estadistica/pokemonObjetivo.getDefensa() + "\n ------------------------------");
        }
        else if (getTipoAtaque().toUpperCase().equals("ESPECIAL")) {
            if ((damageCalc(pokemonCaster, ataqueEsp, pokemonObjetivo.getDefensaEspecial()) <= 0)){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) - damageCalc(pokemonCaster, ataqueEsp, pokemonObjetivo.getDefensa()));

            System.out.println("Daño: " + estadistica/pokemonObjetivo.getDefensaEspecial() + "\n ------------------------------");
        }
    }

    public int damageCalc(Pokemon pokemon, int ataque, int defensa){
        float damageNivel = (float) (2*pokemon.getNivel()/5 + 2);
        float potencia = (float) getPotencia()/100;
        float ataDef = (float) ataque/defensa;
        return ((int)(damageNivel * potencia * ataDef)+2);
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
