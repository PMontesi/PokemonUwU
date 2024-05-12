package es.cesur.progprojectpok.clases;

public class MovimientoEstado extends Movimiento{
    //Estudiar el cambiarlo a un Enum
    String estado;
    int duracionTurnos;
    //HACERLO QUE NO VA A FUNCIONAR
    EstPersitentesEnum estPersitentesEnum;
    EstTemporalesEnum estTemporalesEnum;

    public MovimientoEstado(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoEstado(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, String estado, int duracionTurnos, int idMovimiento) {
        super(nombre, ppMax, ppRest, tipo, prioridad, idMovimiento);
        this.estado = estado;
        this.duracionTurnos = duracionTurnos;
    }

    public void aplicarEstado(Pokemon pokemon, String estado){
        if (estadosPersitentesStringtoEnum(estado) != null && pokemon.getEstadosPersistentes() == EstPersitentesEnum.SALUDABLE){
            switch (estadosPersitentesStringtoEnum(estado)){
                case PARALIZADO, QUEMADO, ENVENENADO, GRAV_ENVENENADO, CONGELADO, HELADO,
                        SOMNOLIENTO, POKERUS, DEBILITADO
                        ->{
                    pokemon.setEstadosPersistentes(estadosPersitentesStringtoEnum(estado));
                    pokemon.setDuracionPersistente(duracionTurnos);
                }
                case DORMIDO -> {
                    pokemon.setEstadosPersistentes(estadosPersitentesStringtoEnum(estado));
                    pokemon.setDuracionPersistente((int) (Math.random()*3) + 1);
                }
            }
        }
        else if (estadosTemporalesStringtoEnum(estado) != null && !pokemon.getEstTemporalesEnums().contains(estadosTemporalesStringtoEnum(estado))) {
            switch (estadosTemporalesStringtoEnum(estado)){
                case ENAMORADO, ATRAPADO, MALDITO, DRENADORAS, CANTO_MORTAL,
                        AMEDRENTADO -> pokemon.setEstTemporal(estadosTemporalesStringtoEnum(estado));
                case CONFUSION -> {
                    pokemon.setEstTemporal(estadosTemporalesStringtoEnum(estado));
                    pokemon.setDuracionConfusion((int) (Math.random()*4) + 1);
                }
            }
        }
    }

    public static void damageOverTime(Pokemon pokemon){

        if(pokemon.getEstadosPersistentes() == EstPersitentesEnum.GRAV_ENVENENADO
                || pokemon.getEstadosPersistentes() == EstPersitentesEnum.ENVENENADO
                ||pokemon.getEstadosPersistentes() == EstPersitentesEnum.QUEMADO
                && pokemon.getDuracionPersistente() > 0){

            int dot = pokemon.getVitMax()/16;
            if(pokemon.getEstadosPersistentes() == EstPersitentesEnum.GRAV_ENVENENADO){
                dot = (pokemon.getVitMax()/16)*(6-pokemon.getDuracionPersistente());
            }
            if (dot <= 0) dot = 1;

            pokemon.setVitalidad(pokemon.getVitalidad()-dot);
            pokemon.setDuracionPersistente(pokemon.getDuracionPersistente()-1);

            quitarEstado(pokemon);

        }
        if (pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.MALDITO)){
            pokemon.setVitalidad(pokemon.getVitalidad()-(pokemon.getVitMax()/4));
        }

    }

    public static void quitarEstado(Pokemon pokemon){
        if (pokemon.getDuracionPersistente() == 0){
            pokemon.setEstadosPersistentes(EstPersitentesEnum.SALUDABLE);
        }

        if (pokemon.getEstadosPersistentes() == EstPersitentesEnum.CONGELADO && (((int) (Math.random() * 5 + 1) == 1))) {
            pokemon.setEstadosPersistentes(EstPersitentesEnum.SALUDABLE);
        }
    }

    public static void quitarConfusion(Pokemon pokemon){
        if (pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.CONFUSION) && pokemon.getDuracionConfusion() == 0 ){
            pokemon.getEstTemporalesEnums().remove(EstTemporalesEnum.CONFUSION);
        }
    }

    public static String estadosPersistentesEnumToString (EstPersitentesEnum estPersitentesEnum){
        return estPersitentesEnum.name();
    }

    public static EstPersitentesEnum estadosPersitentesStringtoEnum (String estadoPersistenteString){
        try {
            return EstPersitentesEnum.valueOf(estadoPersistenteString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static String estadosTemporalesEnumToString (EstPersitentesEnum estadosTemporales){
        return estadosTemporales.name();
    }

    public static EstTemporalesEnum estadosTemporalesStringtoEnum (String estadosTemporalesString){
        try{
            return EstTemporalesEnum.valueOf(estadosTemporalesString.toUpperCase());
        }catch (IllegalArgumentException e){
            return null;
        }

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getDuracionTurnos() {
        return duracionTurnos;
    }

    public void setDuracionTurnos(int duracionTurnos) {
        this.duracionTurnos = duracionTurnos;
    }

    @Override
    public String toString() {
        return super.toString() + "MovimientoEstado{" +
                "estado='" + estado + '\'' +
                ", duracionTurnos=" + duracionTurnos +
                '}';
    }
}
