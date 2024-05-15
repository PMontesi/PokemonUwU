package es.cesur.progprojectpok.clases;

import java.util.Random;

public class MovimientoAtaque extends Movimiento{
    int potencia;
    String tipoAtaque;
    Random r = new Random();

    public MovimientoAtaque(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoAtaque(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, int potencia, String tipoAtaque, int idMovimiento) {
        super(nombre, ppMax, ppRest, tipo, prioridad, idMovimiento);
        this.potencia = potencia;
        this.tipoAtaque = tipoAtaque;
    }

    public MovimientoAtaque(String nombre, int ppMax, int ppRest, Tipos tipo, int potencia, String tipoAtaque) {
        super(nombre, ppMax, ppRest, tipo);
        this.potencia = potencia;
        this.tipoAtaque = tipoAtaque;
    }

    //Pedir AT/AT_ESP como argumentos
    public void aplicarDamage(Pokemon pokemonObjetivo, Pokemon pokemonCaster){
        int ataque = pokemonCaster.getAtaque();
        if (pokemonCaster.getEstadosPersistentes() == EstPersitentesEnum.QUEMADO)  ataque *= 0.5;
        if (ataque <= 0) ataque = 1;

        int ataqueEsp = pokemonCaster.getAtaqueEspecial();
        if (pokemonCaster.getEstadosPersistentes() == EstPersitentesEnum.HELADO) ataqueEsp *= 0.5;
        if (ataqueEsp <= 0) ataqueEsp = 1;

        float multEstado = 1f;
        if (pokemonObjetivo.getEstadosPersistentes() == EstPersitentesEnum.SOMNOLIENTO) multEstado += 0.5f;

        if (pokemonCaster.getEstTemporalesEnums().contains(EstTemporalesEnum.CONFUSION) && (r.nextInt(3)+ 1) == 1){
            pokemonCaster.setVitalidad((int) ((pokemonCaster.getVitalidad()) - (damageConfusion(pokemonCaster, ataque, pokemonCaster.getDefensa())) * multEstado));
        }

        if(getTipoAtaque().equalsIgnoreCase("FISICO")){

            if((damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2()) <= 0)){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((int) ((pokemonObjetivo.getVitalidad()) - (damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2())) * multEstado));
        }
        else if (getTipoAtaque().equalsIgnoreCase("ESPECIAL")) {
            if ((damageCalc(pokemonCaster, ataqueEsp, pokemonObjetivo.getDefensaEspecial(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2()) <= 0)){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) - damageCalc(pokemonCaster, ataqueEsp, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2()));
        }
    }

    public int damageCalc(Pokemon pokemon, int ataque, int defensaObjetivo, Tipos tipo1Objetivo, Tipos tipo2Objetivo){
        float damageNivel = ((float) (2 * pokemon.getNivel()) /5 + 2);
        float potencia = getPotencia();
        float ataDef = (float) ataque/defensaObjetivo;
        float stab = (pokemon.getTipo1() == getTipo() || pokemon.getTipo2() == getTipo()) ? 1.5f : 1;
        float rdm = (float) (1 - ((Math.random()*16)/100));

        return (int) (((damageNivel * potencia * ataDef)/50+2) * stab * compararTipos(tipo1Objetivo) * compararTipos(tipo2Objetivo) * rdm);
    }

    public int damageConfusion (Pokemon pokemon, int ataque, int defensa){

        float damageNivel = ((float) (2 * pokemon.getNivel()) /5 + 2);
        float potencia = (float) 40 / 100;
        float ataDef = (float) ataque/defensa;
        float rdm = (float) (1 - ((Math.random()*16)/100));

        return (int) (((damageNivel * potencia * ataDef)/50+2) * rdm);
    }

    public float compararTipos(Tipos tipoObjetivo) {
        switch (getTipo()) {
            case AGUA:
                if (tipoObjetivo == Tipos.AGUA || tipoObjetivo == Tipos.DRAGON || tipoObjetivo == Tipos.PLANTA)
                    return 0.5f;
                if (tipoObjetivo == Tipos.FUEGO || tipoObjetivo == Tipos.ROCA || tipoObjetivo == Tipos.TIERRA)
                    return 2.0f;
                break;
            case BICHO:
                if (tipoObjetivo == Tipos.FUEGO || tipoObjetivo == Tipos.FANTASMA || tipoObjetivo == Tipos.LUCHA || tipoObjetivo == Tipos.VOLADOR)
                    return 0.5f;
                if (tipoObjetivo == Tipos.PLANTA || tipoObjetivo == Tipos.PSIQUICO || tipoObjetivo == Tipos.VENENO)
                    return 2.0f;
                break;
            case DRAGON:
                if (tipoObjetivo == Tipos.DRAGON)
                    return 2.0f;
                break;
            case ELECTRICO:
                if (tipoObjetivo == Tipos.TIERRA)
                    return 0.0f;
                if (tipoObjetivo == Tipos.ELECTRICO || tipoObjetivo == Tipos.DRAGON || tipoObjetivo == Tipos.PLANTA)
                    return 0.5f;
                if (tipoObjetivo == Tipos.AGUA || tipoObjetivo == Tipos.VOLADOR)
                    return 2.0f;
                break;
            case FANTASMA:
                if (tipoObjetivo == Tipos.NORMAL || tipoObjetivo == Tipos.PSIQUICO)
                    return 0.0f;
                if (tipoObjetivo == Tipos.FANTASMA)
                    return 2.0f;
                break;
            case FUEGO:
                if (tipoObjetivo == Tipos.FUEGO || tipoObjetivo == Tipos.ROCA || tipoObjetivo == Tipos.AGUA || tipoObjetivo == Tipos.DRAGON)
                    return 0.5f;
                if (tipoObjetivo == Tipos.PLANTA || tipoObjetivo == Tipos.HIELO || tipoObjetivo == Tipos.BICHO)
                    return 2.0f;
                break;
            case HIELO:
                if (tipoObjetivo == Tipos.HIELO || tipoObjetivo == Tipos.AGUA)
                    return 0.5f;
                if (tipoObjetivo == Tipos.DRAGON || tipoObjetivo == Tipos.PLANTA || tipoObjetivo == Tipos.ROCA|| tipoObjetivo == Tipos.VOLADOR)
                    return 2.0f;
                break;
            case PLANTA:
                if (tipoObjetivo == Tipos.BICHO || tipoObjetivo == Tipos.DRAGON || tipoObjetivo == Tipos.FUEGO || tipoObjetivo == Tipos.PLANTA || tipoObjetivo == Tipos.VENENO || tipoObjetivo == Tipos.VOLADOR)
                    return 0.5f;
                if (tipoObjetivo == Tipos.AGUA || tipoObjetivo == Tipos.ROCA|| tipoObjetivo == Tipos.TIERRA)
                    return 2.0f;
                break;
            case LUCHA:
                if (tipoObjetivo == Tipos.FANTASMA)
                    return 0.0f;
                if (tipoObjetivo == Tipos.BICHO || tipoObjetivo == Tipos.PSIQUICO || tipoObjetivo == Tipos.VENENO || tipoObjetivo == Tipos.VOLADOR)
                    return 0.5f;
                if (tipoObjetivo == Tipos.HIELO || tipoObjetivo == Tipos.NORMAL || tipoObjetivo == Tipos.ROCA)
                    return 2.0f;
                break;
            case NORMAL:
                if (tipoObjetivo == Tipos.FANTASMA)
                    return 0.0f;
                else if (tipoObjetivo == Tipos.ROCA)
                    return 0.5f;
                break;
            case PSIQUICO:
                if (tipoObjetivo == Tipos.PSIQUICO)
                    return 0.5f;
                if (tipoObjetivo == Tipos.LUCHA || tipoObjetivo == Tipos.VENENO)
                    return 2.0f;
                break;
            case ROCA:
                if (tipoObjetivo == Tipos.LUCHA || tipoObjetivo == Tipos.TIERRA)
                    return 0.5f;
                else if (tipoObjetivo == Tipos.HIELO || tipoObjetivo == Tipos.BICHO || tipoObjetivo == Tipos.FUEGO || tipoObjetivo == Tipos.VOLADOR)
                    return 2.0f;
                break;
            case TIERRA:
                if (tipoObjetivo == Tipos.VOLADOR)
                    return 0.0f;
                if (tipoObjetivo == Tipos.DRAGON || tipoObjetivo == Tipos.PLANTA)
                    return 0.5f;
                if (tipoObjetivo == Tipos.ELECTRICO || tipoObjetivo == Tipos.FUEGO || tipoObjetivo == Tipos.ROCA || tipoObjetivo == Tipos.VENENO)
                    return 2.0f;
                break;
            case VENENO:
                if (tipoObjetivo == Tipos.FANTASMA || tipoObjetivo == Tipos.ROCA || tipoObjetivo == Tipos.TIERRA || tipoObjetivo == Tipos.VENENO)
                    return 0.5f;
                if (tipoObjetivo == Tipos.BICHO|| tipoObjetivo == Tipos.PLANTA)
                    return 2.0f;
                break;
            case VOLADOR:
                if (tipoObjetivo == Tipos.ELECTRICO || tipoObjetivo == Tipos.ROCA)
                    return 0.5f;
                if (tipoObjetivo == Tipos.PLANTA || tipoObjetivo == Tipos.LUCHA || tipoObjetivo == Tipos.BICHO)
                    return 2.0f;
                break;
            default:
                return 1.0f;
        }
        return 1.0f;
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
