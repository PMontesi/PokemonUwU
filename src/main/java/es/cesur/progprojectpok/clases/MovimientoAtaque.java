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

        if (pokemonCaster.getEstTemporalesEnums().contains(EstTemporalesEnum.CONFUSION) && (((int) (Math.random() * 3 + 1) == 1))){
            pokemonCaster.setVitalidad((int) ((pokemonCaster.getVitalidad()) - (damageConfusion(pokemonCaster, ataque, pokemonCaster.getDefensa())) * multEstado));
        }

        if(getTipoAtaque().toUpperCase().equals("FISICO")){

            if((damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2()) <= 0)){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((int) ((pokemonObjetivo.getVitalidad()) - (damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2())) * multEstado));

            System.out.println("Daño: " + ((int) (damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2())) * multEstado) + "\n ------------------------------");
        }
        else if (getTipoAtaque().toUpperCase().equals("ESPECIAL")) {
            if ((damageCalc(pokemonCaster, ataqueEsp, pokemonObjetivo.getDefensaEspecial(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2()) <= 0)){
                pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) -1);
            }
            else pokemonObjetivo.setVitalidad((pokemonObjetivo.getVitalidad()) - damageCalc(pokemonCaster, ataqueEsp, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2()));

            System.out.println("Daño: " + ((int) (damageCalc(pokemonCaster, ataque, pokemonObjetivo.getDefensa(), pokemonObjetivo.getTipo1(), pokemonObjetivo.getTipo2())) * multEstado) + "\n ------------------------------");
        }
    }

    public int damageCalc(Pokemon pokemon, int ataque, int defensa, Tipos tipo1Objetivo, Tipos tipo2Objetivo){
        float damageNivel = (float) (2*pokemon.getNivel()/5 + 2);
        float potencia = (float) getPotencia();
        float ataDef = (float) ataque/defensa;
        float stab = (pokemon.getTipo1() == getTipo() || pokemon.getTipo2() == getTipo()) ? 1.5f : 1;
        float rdm = (float) (1 - ((Math.random()*16)/100));






        return (int) (((damageNivel * potencia * ataDef)/50+2) * stab * compararTipos(tipo1Objetivo) * compararTipos(tipo2Objetivo) * rdm);
    }

    public int damageConfusion (Pokemon pokemon, int ataque, int defensa){

        float damageNivel = (float) (2*pokemon.getNivel()/5 + 2);
        float potencia = (float) 40 / 100;
        float ataDef = (float) ataque/defensa;
        float rdm = (float) (1 - ((Math.random()*16)/100));

        return (int) (((damageNivel * potencia * ataDef)/50+2) * rdm);
    }

    public float compararTipos(Tipos tipo) {
        switch (getTipo()) {
            case AGUA:
                if (tipo == Tipos.AGUA || tipo == Tipos.DRAGON || tipo == Tipos.PLANTA)
                    return 0.5f;
                else if (tipo == Tipos.FUEGO || tipo == Tipos.ROCA || tipo == Tipos.TIERRA)
                    return 2.0f;
                break;
            case BICHO:
                if (tipo == Tipos.FUEGO || tipo == Tipos.FANTASMA || tipo == Tipos.LUCHA || tipo == Tipos.VOLADOR)
                    return 0.5f;
                else if (tipo == Tipos.PLANTA || tipo == Tipos.PSIQUICO || tipo == Tipos.VENENO)
                    return 2.0f;
                break;
            case DRAGON:
                if (tipo == Tipos.DRAGON)
                    return 2.0f;
                break;
            case ELECTRICO:
                if (tipo == Tipos.TIERRA)
                    return 0.0f;
                else if (tipo == Tipos.ELECTRICO || tipo == Tipos.DRAGON || tipo == Tipos.PLANTA)
                    return 0.5f;
                else if (tipo == Tipos.AGUA || tipo == Tipos.VOLADOR)
                    return 2.0f;
                break;
            case  FANTASMA:
                if (tipo == Tipos.NORMAL || tipo == Tipos.PSIQUICO)
                    return 0.0f;
                else if (tipo == Tipos.FANTASMA)
                    return 2.0f;
                break;
            case FUEGO:
                if (tipo == Tipos.FUEGO || tipo == Tipos.ROCA || tipo == Tipos.AGUA || tipo == Tipos.DRAGON)
                    return 0.5f;
                else if (tipo == Tipos.PLANTA || tipo == Tipos.HIELO || tipo == Tipos.BICHO)
                    return 2.0f;
                break;
            case HIELO:
                if (tipo == Tipos.HIELO || tipo == Tipos.AGUA)
                    return 0.5f;
                else if (tipo == Tipos.DRAGON || tipo == Tipos.PLANTA || tipo == Tipos.ROCA|| tipo == Tipos.VOLADOR)
                    return 2.0f;
                break;
            case PLANTA:
                if (tipo == Tipos.BICHO || tipo == Tipos.DRAGON || tipo == Tipos.FUEGO || tipo == Tipos.PLANTA || tipo == Tipos.VENENO || tipo == Tipos.VOLADOR)
                    return 0.5f;
                else if (tipo == Tipos.AGUA || tipo == Tipos.ROCA|| tipo == Tipos.TIERRA)
                    return 2.0f;
                break;
            case LUCHA:
                if (tipo == Tipos.FANTASMA)
                    return 0.0f;
                else if (tipo == Tipos.BICHO || tipo == Tipos.PSIQUICO || tipo == Tipos.VENENO || tipo == Tipos.VOLADOR)
                    return 0.5f;
                else if (tipo == Tipos.HIELO || tipo == Tipos.NORMAL || tipo == Tipos.ROCA)
                    return 2.0f;
                break;
            case NORMAL:
                if (tipo == Tipos.FANTASMA)
                    return 0.0f;
                else if (tipo == Tipos.ROCA)
                    return 0.5f;
                break;
            case PSIQUICO:
                if (tipo == Tipos.PSIQUICO)
                    return 0.5f;
                else if (tipo == Tipos.LUCHA || tipo == Tipos.VENENO)
                    return 2.0f;
                break;
            case ROCA:
                if (tipo == Tipos.LUCHA || tipo == Tipos.TIERRA)
                    return 0.5f;
                else if (tipo == Tipos.HIELO || tipo == Tipos.BICHO || tipo == Tipos.FUEGO || tipo == Tipos.VOLADOR)
                    return 2.0f;
                break;
            case TIERRA:
                if (tipo == Tipos.VOLADOR)
                    return 0.0f;
                else if (tipo == Tipos.DRAGON || tipo == Tipos.PLANTA)
                    return 0.5f;
                else if (tipo == Tipos.ELECTRICO || tipo == Tipos.FUEGO || tipo == Tipos.ROCA || tipo == Tipos.VENENO)
                    return 2.0f;
                break;
            case VENENO:
                if (tipo == Tipos.FANTASMA || tipo == Tipos.ROCA || tipo == Tipos.TIERRA || tipo == Tipos.VENENO)
                    return 0.5f;
                else if (tipo == Tipos.BICHO|| tipo == Tipos.PLANTA)
                    return 2.0f;
                break;
            case VOLADOR:
                if (tipo == Tipos.ELECTRICO || tipo == Tipos.ROCA)
                    return 0.5f;
                else if (tipo == Tipos.PLANTA || tipo == Tipos.LUCHA || tipo == Tipos.BICHO)
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
