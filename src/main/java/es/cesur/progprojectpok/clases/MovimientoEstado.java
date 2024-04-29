package es.cesur.progprojectpok.clases;

public class MovimientoEstado extends Movimiento{
    //Estudiar el cambiarlo a un Enum
    String estado;
    int duracionTurnos;

    public MovimientoEstado(String nombre, int idMovimiento) {
        super(nombre, idMovimiento);
    }

    public MovimientoEstado(String nombre, int ppMax, int ppRest, Tipos tipo, int prioridad, String estado, int duracionTurnos) {
        super(nombre, ppMax, ppRest, tipo, prioridad);
        this.estado = estado;
        this.duracionTurnos = duracionTurnos;
    }

    public void aplicarEstado(Pokemon pokemon, String estado){
        if (estadosPersitentesStringtoEnum(estado) != null){
            switch (estadosPersitentesStringtoEnum(estado)){
                case PARALIZADO, QUEMADO, ENVENENADO, GRAV_ENVENENADO, DORMIDO, CONGELADO, HELADO,
                        SOMNOLIENTO, POKERUS, DEBILITADO, SALUDABLE
                        -> pokemon.setEstadosPersistentes(estadosPersitentesStringtoEnum(estado));
            }
        }
        else if (estadosTemporalesStringtoEnum(estado) != null) {
            switch (estadosTemporalesStringtoEnum(estado)){
                case CONFUSO, ENAMORADO, ATRAPADO, MALDITO, DRENADORAS, CANTO_MORTAL, CENTRO_ATENCION,
                        AMEDRENTADO, NINGUNO
                        -> pokemon.setEstadosTemporales(estadosTemporalesStringtoEnum(estado));
            }
        }
    }

    public static String estadosPersistentesEnumToString (EstadosPersitentes estadosPersitentes){
        return estadosPersitentes.name();
    }

    public static EstadosPersitentes estadosPersitentesStringtoEnum (String estadoPersistenteString){
        return EstadosPersitentes.valueOf(estadoPersistenteString.toUpperCase());
    }

    public static String estadosTemporalesEnumToString (EstadosPersitentes estadosTemporales){
        return estadosTemporales.name();
    }

    public static EstadosTemporales estadosTemporalesStringtoEnum (String estadosTemporalesString){
        return EstadosTemporales.valueOf(estadosTemporalesString.toUpperCase());
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
}
