package es.cesur.progprojectpok.clases;

public class Pokemon {

    private String nombre;
    private String mote;
    private int vitalidad;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    private int velocidad;
    private int nivel;
    private int experiencia;
    private char sexo;
    private int fertilidad;
    private static final int VALOR_INICIAL_FERT = 5;
    private Tipos tipo1;
    private Tipos tipo2;
    private static Movimiento[] movimientos = new Movimiento[4];
    private EstadoPersistente estadoPersistente;
    private EstadoTemporal estadotemporal;
    private Objeto objetoEquipado;

    public Pokemon() {
    }

    //Constructor de pokemons que nacen. Principalmente para la pantalla de captura.
    public Pokemon(String nombre, Tipos tipo1, Tipos tipo2) {
        this.nombre = nombre;
        this.vitalidad = (int) (Math.random()*10 + 1);
        this.ataque = (int) (Math.random()*10 + 1);
        this.defensa = (int) (Math.random()*10 + 1);
        this.ataqueEspecial = (int) (Math.random()*10 + 1);
        this.defensaEspecial = (int) (Math.random()*10 + 1);
        this.velocidad = (int) (Math.random()*10 + 1);
        this.nivel = 1;
        this.experiencia = 0;
        this.sexo = randomSex();
        this.fertilidad = VALOR_INICIAL_FERT;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
    }

    public char randomSex(){
        char sexo;
        int sexoBinario = (int) (Math.random()*2);
        if (sexoBinario == 0) sexo = 'H';
        else sexo = 'M';
        return sexo;
    }

    public static Tipos TipoStringToEnum(String tipoString){
        return switch (tipoString.toUpperCase()) {
            case "AGUA" -> Tipos.AGUA;
            case "BICHO" -> Tipos.BICHO;
            case "DRAGÓN" -> Tipos.DRAGON;
            case "ELÉCTRICO" -> Tipos.ELECTRICO;
            case "FANTAMAS" -> Tipos.FANTASMA;
            case "FUEGO" -> Tipos.FUEGO;
            case "HIELO" -> Tipos.HIELO;
            case "LUCHA" -> Tipos.LUCHA;
            case "NORMAL" -> Tipos.NORMAL;
            case "PLANTA" -> Tipos.PLANTA;
            case "PSÍQUICO" -> Tipos.PSIQUICO;
            case "ROCA" -> Tipos.ROCA;
            case "TIERRA" -> Tipos.TIERRA;
            case "VENENO" -> Tipos.VENENO;
            case "VOLADOR" -> Tipos.VOLADOR;
            case "NULL" -> Tipos.NULL;
            default -> null;
        };
    }


    //GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMote() {
        return mote;
    }

    public void setMote(String mote) {
        this.mote = mote;
    }

    public int getVitalidad() {
        return vitalidad;
    }

    public void setVitalidad(int vitalidad) {
        this.vitalidad = vitalidad;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public void setAtaqueEspecial(int ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public void setDefensaEspecial(int defensaEspecial) {
        this.defensaEspecial = defensaEspecial;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getFertilidad() {
        return fertilidad;
    }

    public void setFertilidad(int fertilidad) {
        this.fertilidad = fertilidad;
    }

    public Tipos getTipo1() {
        return tipo1;
    }

    public void setTipo1(Tipos tipo1) {
        this.tipo1 = tipo1;
    }

    public Tipos getTipo2() {
        return tipo2;
    }

    public void setTipo2(Tipos tipo2) {
        this.tipo2 = tipo2;
    }

    public Movimiento[] getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimiento[] movimientos) {
        this.movimientos = movimientos;
    }

    public EstadoPersistente getEstadoPersistente() {
        return estadoPersistente;
    }

    public void setEstadoPersistente(EstadoPersistente estadoPersistente) {
        this.estadoPersistente = estadoPersistente;
    }

    public EstadoTemporal getEstadotemporal() {
        return estadotemporal;
    }

    public void setEstadotemporal(EstadoTemporal estadotemporal) {
        this.estadotemporal = estadotemporal;
    }

    public Objeto getObjetoEquipado() {
        return objetoEquipado;
    }

    public void setObjetoEquipado(Objeto objetoEquipado) {
        this.objetoEquipado = objetoEquipado;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Pokemon{" +
                "nombre='" + nombre + '\'' +
                ", mote='" + mote + '\'' +
                ", vitalidad=" + vitalidad +
                ", ataque=" + ataque +
                ", defensa=" + defensa +
                ", ataqueEspecial=" + ataqueEspecial +
                ", defensaEspecial=" + defensaEspecial +
                ", velocidad=" + velocidad +
                ", nivel=" + nivel +
                ", experiencia=" + experiencia +
                ", sexo=" + sexo +
                ", fertilidad=" + fertilidad +
                ", tipo1=" + tipo1 +
                ", tipo2=" + tipo2 +
                ", estadoPersistente=" + estadoPersistente +
                ", estadotemporal=" + estadotemporal +
                ", objetoEquipado=" + objetoEquipado +
                '}';
    }
}
