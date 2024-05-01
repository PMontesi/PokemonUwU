package es.cesur.progprojectpok.clases;

public class EstadoPersistente {
    int duracion;

    public EstadoPersistente() {
    }

    public EstadoPersistente(int duracion) {
        this.duracion = duracion;
    }

    public void envenenar(Pokemon pokemon){
        int menosvida = pokemon.getVitalidad()/16;
        pokemon.setVitalidad(pokemon.getVitalidad()-menosvida);
        setDuracion(getDuracion()-1);
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
