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

    /**
     * Resta vitalidad del pokemon objetivo según el resultado de la fórmula de daño.
     * Cambia las estadísticas si el pokemon sufre alguno de los estados alterados correspondientes.
     *
     * @param pokemonObjetivo el pokemon que sufre el ataque
     * @param pokemonCaster el pokemon que realiza el atauqe
     */
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

    /**
     * Formula para calcular el daño que realiza un movimiento.
     * damageNivel: calcula el daño que tiene que realizar según el niveld el atacante.
     * potencia: consigue la potencia del movimiento usado.
     * ataDef: la división de la estadística de ataque del atacante entre la estadística de defensa del objetivo.
     * Estas tres variables anteriores se multiplican entre sí. El resultado de la multiplicación se divide entre 50 y
     * se le suma 2. El resto de variables multiplican este resulado.
     * -
     * stab: comprueba si el movimiento usado coincide con alguno de los tipos del pokemon. En caso verdero, devuelve 1,5.
     * La fórmula continúa con las ventajas y las desventajas.
     *
     * @param pokemon pokemon que realiza el ataque
     * @param ataque el valor de la estadística de ataque correspondiente del pokemon objetivo
     * @param defensaObjetivo el valor de la estadística de defensa correspondiente del pokemon objetivo
     * @param tipo1Objetivo el enumerado del tipo del pokemon objetivo.
     * @param tipo2Objetivo el enumerado dle tipo del pokemon objetivo. Puede ser nulo.
     * @return el reusltado de la fórmula.
     */
    public int damageCalc(Pokemon pokemon, int ataque, int defensaObjetivo, Tipos tipo1Objetivo, Tipos tipo2Objetivo){
        float damageNivel = ((float) (2 * pokemon.getNivel()) /5 + 2);
        float potencia = getPotencia();
        float ataDef = (float) ataque/defensaObjetivo;
        float stab = (pokemon.getTipo1() == getTipo() || pokemon.getTipo2() == getTipo()) ? 1.5f : 1;
        float rdm = (float) (1 - ((Math.random()*16)/100));

        return (int) (((damageNivel * potencia * ataDef)/50+2) * stab * compararTipos(tipo1Objetivo) * compararTipos(tipo2Objetivo) * rdm);
    }

    /**
     * Fórmula para calcular el daño cuando un pokemon está confuso y se hace daño así mismo.
     *
     * @param pokemon pokemon que tiene la confusión y ataca,
     * @param ataque el valor de la estadística ataque del pokemon,
     * @param defensa el valor de la estadistica defensa del pokemon,
     * @return el resultado de la fórmula,
     */
    public int damageConfusion (Pokemon pokemon, int ataque, int defensa){

        float damageNivel = ((float) (2 * pokemon.getNivel()) /5 + 2);
        float potencia = (float) 40 / 100;
        float ataDef = (float) ataque/defensa;
        float rdm = (float) (1 - ((Math.random()*16)/100));

        return (int) (((damageNivel * potencia * ataDef)/50+2) * rdm);
    }

    /**
     * Tabla de ventajas y desventajas.
     * @param tipoObjetivo el enumerado del tipo del pokemon.
     * @return el multiplicador a usar en la fórmula de ataque.
     */
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
