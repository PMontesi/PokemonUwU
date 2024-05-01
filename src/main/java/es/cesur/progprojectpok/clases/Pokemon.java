package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.*;
import java.util.Arrays;

public class Pokemon {

    private String nombre;
    private String mote;
    private int vitalidad;
    private int vitMax;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    private int velocidad;
    private int prioridad = 0;
    private int nivel;
    private int experiencia;
    private char sexo;
    private int fertilidad;
    private int numPokedex;
    private int id;
    private String imagenUrl;
    private static final int VALOR_INICIAL_FERT = 5;
    private Tipos tipo1;
    private Tipos tipo2;
    private final Movimiento[] MOVIMIENTOS = new Movimiento[4];
    private EstadosPersitentes estadosPersistentes;
    private EstadosTemporales estadosTemporales;
    private Objeto objetoEquipado;

    public Pokemon() {
    }

    //Constructor de pokemons para capturarlos.
    public Pokemon(String nombre, int numPokedex) {
        this.nombre = nombre;
        this.vitalidad =    5 + (int) (Math.random()*10 + 1);
        this.ataque =           (int) (Math.random()*10 + 1);
        this.defensa =          (int) (Math.random()*10 + 1);
        this.ataqueEspecial =   (int) (Math.random()*10 + 1);
        this.defensaEspecial =  (int) (Math.random()*10 + 1);
        this.velocidad =        (int) (Math.random()*10 + 1);
        this.nivel = 1;
        this.experiencia = 0;
        this.sexo = randomSex();
        this.numPokedex = numPokedex;
        this.fertilidad = VALOR_INICIAL_FERT;
    }

    //Constructor para el combate pokemon. Principalmente para los pokemons del usuario.
    public Pokemon(String mote, int vitalidad, int vitMax, int ataque, int defensa, int ataqueEspecial, int defensaEspecial,
                   int velocidad, int nivel, int experiencia, int numPokedex, int id, String imagenUrl, String tipo1, String tipo2,
                   String estadosPersistentes, String estadosTemporales, Objeto objetoEquipado) {
        this.mote = mote;
        this.vitalidad = vitalidad;
        this.vitMax = vitMax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.numPokedex = numPokedex;
        this.id = id;
        this.imagenUrl = imagenUrl;
        this.tipo1 = Pokemon.TipoStringToEnum(tipo1);
        this.tipo2 = Pokemon.TipoStringToEnum(tipo2);
        this.estadosPersistentes = MovimientoEstado.estadosPersitentesStringtoEnum(estadosPersistentes);
        this.estadosTemporales = MovimientoEstado.estadosTemporalesStringtoEnum(estadosTemporales);
        this.objetoEquipado = objetoEquipado;
    }

    //Constructor para pokemons que no estén en la BBDD para usar en combate.
    //Por ahora solo para la vista combate (probablemente también para la de entrenamiento)
    public Pokemon(String nombre, int mediaNivel, int numPokedex, String imagenUrl, String tipo1, String tipo2, Objeto objetoEquipado) {
        this.nombre = nombre;
        this.mote = nombre;
        this.nivel = mediaNivel + nivelAleatorio();
            if (this.nivel < 1) this.nivel = 1;
        this.ataque =           (int) ((Math.random()*10 + 1) + subidaEstadisticasInstananea(this.nivel));
        this.defensa =          (int) ((Math.random()*10 + 1) + subidaEstadisticasInstananea(this.nivel));
        this.ataqueEspecial =   (int) ((Math.random()*10 + 1) + subidaEstadisticasInstananea(this.nivel));
        this.defensaEspecial =  (int) ((Math.random()*10 + 1) + subidaEstadisticasInstananea(this.nivel));
        this.velocidad =        (int) ((Math.random()*10 + 1) + subidaEstadisticasInstananea(this.nivel));
        this.vitalidad =    5 + (int) ((Math.random()*10 + 1) + subidaEstadisticasInstananea(this.nivel));
        this.vitMax = this.vitalidad;
        this.sexo = randomSex();
        this.numPokedex = numPokedex;
        this.imagenUrl = imagenUrl;
        this.tipo1 = Pokemon.TipoStringToEnum(tipo1);
        this.tipo2 = Pokemon.TipoStringToEnum(tipo2);
        this.estadosPersistentes = EstadosPersitentes.SALUDABLE;
        this.estadosTemporales = EstadosTemporales.NINGUNO;
        this.objetoEquipado = objetoEquipado;
    }

    public char randomSex(){
        char sexo;
        int sexoBinario = (int) (Math.random()*2);
        if (sexoBinario == 0) sexo = 'H';
        else sexo = 'M';
        return sexo;
    }

    public static Tipos TipoStringToEnum(String tipoString){
        if (tipoString == null){
            tipoString = "null";
        }
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
    public static String TipoEnumToString(Tipos tipoEnum) {
        if (tipoEnum == null) {
            return "null";
        }
        return switch (tipoEnum) {
            case AGUA -> "AGUA";
            case BICHO -> "BICHO";
            case DRAGON -> "DRAGÓN";
            case ELECTRICO -> "ELÉCTRICO";
            case FANTASMA -> "FANTASMA";
            case FUEGO -> "FUEGO";
            case HIELO -> "HIELO";
            case LUCHA -> "LUCHA";
            case NORMAL -> "NORMAL";
            case PLANTA -> "PLANTA";
            case PSIQUICO -> "PSÍQUICO";
            case ROCA -> "ROCA";
            case TIERRA -> "TIERRA";
            case VENENO -> "VENENO";
            case VOLADOR -> "VOLADOR";
            case NULL -> "NULL";
        };
    }

    public void subirNivel(){
        while (experiencia == nivel*10){
            try{Connection connection = DBConnection.getConnection();

                String sqlRecuperarStats = "SELECT * FROM POKEMON WHERE ID_POKEMON = ?";
                PreparedStatement statementRecuperarStats = connection.prepareStatement(sqlRecuperarStats);
                statementRecuperarStats.setInt(1, getId());
                ResultSet resultSetRecuperarStats = statementRecuperarStats.executeQuery();
                setAtaque(resultSetRecuperarStats.getInt("ATAQUE"));
                setAtaqueEspecial(resultSetRecuperarStats.getInt("AT_ESPECIAL"));
                setDefensa(resultSetRecuperarStats.getInt("DEFENSA"));
                setDefensaEspecial(resultSetRecuperarStats.getInt("DEF_ESPECIAL"));
                setVelocidad(resultSetRecuperarStats.getInt("VELOCIDAD"));
                setVitalidad(resultSetRecuperarStats.getInt("VITALIDAD"));

                String sqlSubirNivel = "UPDATE POKEMON\n" +
                        "SET \n" +
                        "\tATAQUE = ?, \n" +
                        "    AT_ESPECIAL = ?, \n" +
                        "    DEFENSA = ?, \n" +
                        "    DEF_ESPECIAL = ?,\n" +
                        "    VELOCIDAD = ?\n" +
                        "    VITALIDAD = ?,\n" +
                        "    EXPERIENCIA = ?,\n" +
                        "    NIVEL= ?\n" +
                        "WHERE ID_POKEMON = ?";

                PreparedStatement statementSubirNivel = connection.prepareStatement(sqlSubirNivel);
                statementSubirNivel.setInt(1, getAtaque() + ((int) ((Math.random()*5 + 1))));
                statementSubirNivel.setInt(2, getAtaqueEspecial() + ((int) ((Math.random()*5 + 1))));
                statementSubirNivel.setInt(3, getDefensa() + ((int) ((Math.random()*5 + 1))));
                statementSubirNivel.setInt(4, getDefensaEspecial() + ((int) ((Math.random()*5 + 1))));
                statementSubirNivel.setInt(5, getVelocidad() + ((int) ((Math.random()*5 + 1))));
                statementSubirNivel.setInt(6, getVitalidad() + ((int) ((Math.random()*5 + 1))));
                statementSubirNivel.setInt(7, getExperiencia() - nivel*10);
                statementSubirNivel.setInt(8, getNivel()+1);
                statementSubirNivel.setInt(9, getId());
                statementSubirNivel.executeUpdate();

            } catch(SQLException e){
                e.printStackTrace();
            }
            setExperiencia(getExperiencia() - nivel*10);
            setNivel(getNivel()+1);
            if(nivel % 4 == 0){
                //YA VEREMOS CÓMO HACEMOS LA MOVIDA DE APRENDER MOVIMIENTOS CUANDO SE SUBA DE NIVEL.
                //ESTO EN VERDAD HAY QUE MOVERLO AL COMBATE Y ENTRENAMIENTO CONTROLLER.
            }
        }
    }

    //SI NO FUNCIONA ES POR LAS PUÑETERAS TILDES.
    /*
    Es posible que haya que divir este método en 2:
    -Uno para saber qué movimiento se va a aprender. Este movimiento debería devoler una instancia de Movimiento
    -Otro para aprenderlo en caos de quererlo, lo que implica que sea un onAction del botón de aceptar.

    Haría falta un tercer método que iría conjuntamente al segundo para olvidar un movimiento.

     */
    public Movimiento selecionarMovimientoDB(){

        String sqlMovimiento = "";
        Movimiento movimiento = null;
        try{
            Connection connection = DBConnection.getConnection();

            //SABER QUÉ MOVIMIENTO SE VA A APRENDER
            PreparedStatement statementMovimiento = null;
            if(getTipo1() == Tipos.NORMAL && getTipo2() == Tipos.NULL){
                sqlMovimiento = "SELECT * FROM MOVIMIENTOS \n" +
                        "WHERE NIVEL_APRENDIZAJE <= ? \n" +
                        "AND TIPO = 'NORMAL'\n" +
                        "AND NOT EXISTS (\n" +
                        "    SELECT *\n" +
                        "    FROM MOVIMIENTOS_POKEMON MP\n" +
                        "    WHERE MP.ID_MOVIMIENTO = MP.ID_MOVIMIENTO AND\n" +
                        "    MP.ID_POKEMON = ?\n" +
                        ")\n" +
                        "ORDER BY RAND() LIMIT 1";
                statementMovimiento = connection.prepareStatement(sqlMovimiento);
                statementMovimiento.setInt(1, getNivel());
                statementMovimiento.setInt(2, getId());
            }
            else if(getTipo1() == Tipos.NORMAL && getTipo2() != Tipos.NULL){
                sqlMovimiento = "SELECT * FROM MOVIMIENTOS \n" +
                        "WHERE NIVEL_APRENDIZAJE <= ? \n" +
                        "AND TIPO = 'NORMAL'\n" +
                        "OR TIPO = ?\n" +
                        "AND NOT EXISTS (\n" +
                        "    SELECT *\n" +
                        "    FROM MOVIMIENTOS_POKEMON MP\n" +
                        "    WHERE MP.ID_MOVIMIENTO = MP.ID_MOVIMIENTO AND\n" +
                        "    MP.ID_POKEMON = ?\n" +
                        ")\n" +
                        "ORDER BY RAND() LIMIT 1";
                statementMovimiento = connection.prepareStatement(sqlMovimiento);
                statementMovimiento.setInt(1, getNivel());
                statementMovimiento.setString(2, TipoEnumToString(getTipo2()));
                statementMovimiento.setInt(3, getId());
            }
            else if(getTipo1() != Tipos.NORMAL && getTipo2() == Tipos.NULL){
                sqlMovimiento = "SELECT * FROM MOVIMIENTOS \n" +
                        "WHERE NIVEL_APRENDIZAJE <= ? \n" +
                        "AND TIPO = 'NORMAL'\n" +
                        "OR TIPO = ?\n" +
                        "AND NOT EXISTS (\n" +
                        "    SELECT *\n" +
                        "    FROM MOVIMIENTOS_POKEMON MP\n" +
                        "    WHERE MP.ID_MOVIMIENTO = MP.ID_MOVIMIENTO AND\n" +
                        "    MP.ID_POKEMON = ?\n" +
                        ")\n" +
                        "ORDER BY RAND() LIMIT 1";
                statementMovimiento = connection.prepareStatement(sqlMovimiento);
                statementMovimiento.setInt(1, getNivel());
                statementMovimiento.setString(2, TipoEnumToString(getTipo1()));
                statementMovimiento.setInt(3, getId());
            }
            else if(getTipo1() != Tipos.NORMAL && getTipo2() != Tipos.NULL){
                sqlMovimiento = "SELECT * FROM MOVIMIENTOS \n" +
                        "WHERE NIVEL_APRENDIZAJE <= ? \n" +
                        "AND TIPO = 'NORMAL'\n" +
                        "OR TIPO = ?\n" +
                        "OR TIPO = ?\n" +
                        "AND NOT EXISTS (\n" +
                        "    SELECT *\n" +
                        "    FROM MOVIMIENTOS_POKEMON MP\n" +
                        "    WHERE MP.ID_MOVIMIENTO = MP.ID_MOVIMIENTO AND\n" +
                        "    MP.ID_POKEMON = ?\n" +
                        ")\n" +
                        "ORDER BY RAND() LIMIT 1";
                statementMovimiento = connection.prepareStatement(sqlMovimiento);
                statementMovimiento.setInt(1, getNivel());
                statementMovimiento.setString(2, TipoEnumToString(getTipo1()));
                statementMovimiento.setString(3, TipoEnumToString(getTipo2()));
                statementMovimiento.setInt(4, getId());
            }

            ResultSet resultSetMovimiento = statementMovimiento.executeQuery();
            while (resultSetMovimiento.next()){
                if(resultSetMovimiento.getString("TIPO_DAÑO") != null){
                    movimiento = new MovimientoAtaque(
                            resultSetMovimiento.getString("NOM_MOVIMIENTO"),
                            resultSetMovimiento.getInt("PP_MAX"),
                            resultSetMovimiento.getInt("PP_REST"),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt("POTENCIA"),
                            resultSetMovimiento.getString("TIPO_DAÑO"),
                            resultSetMovimiento.getInt("ID_MOVIMIENTO")
                    );
                    System.out.println("SE HIZO UN MOVIMIENTO DE ATAQUE");
                    System.out.println(movimiento.toString());
                }
                else if(resultSetMovimiento.getString("ESTADO") != null){
                    movimiento = new MovimientoEstado(
                            resultSetMovimiento.getString("NOM_MOVIMIENTO"),
                            resultSetMovimiento.getInt("PP_MAX"),
                            resultSetMovimiento.getInt("PP_REST"),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getString("ESTADO"),
                            resultSetMovimiento.getInt("TURNOS"),
                            resultSetMovimiento.getInt("ID_MOVIMIENTO")

                    );
                    System.out.println("SE HIZO UN MOVIMIENTO DE ESTADO");
                    System.out.println(movimiento.toString());
                }
                else if(resultSetMovimiento.getString("MEJORA") != null){
                    movimiento = new MovimientoMejora(
                            resultSetMovimiento.getString("NOM_MOVIMIENTO"),
                            resultSetMovimiento.getInt("PP_MAX"),
                            resultSetMovimiento.getInt("PP_REST"),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt("TURNOS"),
                            resultSetMovimiento.getString("MEJORA"),
                            resultSetMovimiento.getInt("CANT_MEJORA"),
                            resultSetMovimiento.getInt("ID_MOVIMIENTO")
                    );
                    System.out.println("SE HIZO UN MOVIMIENTO DE MEJORA");
                    System.out.println(movimiento.toString());
                }
            }
            System.out.println(movimiento.toString());

            resultSetMovimiento.close();
            statementMovimiento.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return movimiento;
    }

    public void aprenderMovimiento(Movimiento movimiento){
        String sqlAprender = "INSERT INTO MOVIMIENTOS_POKEMON (ID_MOVIMIENTO, ID_POKEMON, ACTIVO) VALUES (?, ?, 1)";
        try{
            Connection connection = DBConnection.getConnection();
            PreparedStatement statementAprender = connection.prepareStatement(sqlAprender);
            statementAprender.setInt(1, movimiento.getIdMovimiento());
            statementAprender.setInt(2, getId());
            statementAprender.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //BASTANTES DUDAS DE QUE ESTO FURULE.
    public void usarMovimiento(int indice, Pokemon pokemonObjetivo){
        System.out.println(MOVIMIENTOS[indice].getNombre() + " USADO SOBRE " + pokemonObjetivo.getMote());
        if (MOVIMIENTOS[indice] instanceof MovimientoAtaque){
            if((((MovimientoAtaque) MOVIMIENTOS[indice]).getTipoAtaque().equalsIgnoreCase("FISICO"))){
                ((MovimientoAtaque) MOVIMIENTOS[indice]).aplicarDamage(pokemonObjetivo, getAtaque());
            }
            else if ((((MovimientoAtaque) MOVIMIENTOS[indice]).getTipoAtaque().equalsIgnoreCase("ESPECIAL"))){
                ((MovimientoAtaque) MOVIMIENTOS[indice]).aplicarDamage(pokemonObjetivo, getAtaqueEspecial());
            }
        }
        else if (MOVIMIENTOS[indice] instanceof MovimientoMejora){
            ((MovimientoMejora) MOVIMIENTOS[indice]).mejoraAplica(pokemonObjetivo);
        }
        else if (MOVIMIENTOS[indice] instanceof MovimientoEstado){
            ((MovimientoEstado) MOVIMIENTOS[indice]).aplicarEstado(pokemonObjetivo, ((MovimientoEstado) MOVIMIENTOS[indice]).getEstado());
        }
    }

    //Asigna los movimientos que los pokemons saben.
    //Actualmente no distingue entre los que tienen equipados y los que no.
    public void asignarMovimientos (int pokemonId){
        String sqlMovimiento = "SELECT M.* \n" +
                "FROM MOVIMIENTOS M\n" +
                "INNER JOIN MOVIMIENTOS_POKEMON MD ON MD.ID_MOVIMIENTO = M.ID_MOVIMIENTO\n" +
                "INNER JOIN POKEMON P ON P.ID_POKEMON = MD.ID_POKEMON\n" +
                "WHERE P.ID_POKEMON = ?";
        Movimiento movimiento = null;
        int i = 0;
        try{
            Connection connection = DBConnection.getConnection();
            PreparedStatement statementMovimiento = connection.prepareStatement(sqlMovimiento);
            statementMovimiento.setInt(1, pokemonId);
            ResultSet resultSetMovimiento = statementMovimiento.executeQuery();
            while (resultSetMovimiento.next()){
                if(resultSetMovimiento.getString("TIPO_DAÑO") != null){
                    movimiento = new MovimientoAtaque(
                            resultSetMovimiento.getString("NOM_MOVIMIENTO"),
                            resultSetMovimiento.getInt("PP_MAX"),
                            resultSetMovimiento.getInt("PP_REST"),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt("POTENCIA"),
                            resultSetMovimiento.getString("TIPO_DAÑO"),
                            resultSetMovimiento.getInt("ID_MOVIMIENTO")
                    );
                    System.out.println("SE HIZO UN MOVIMIENTO DE ATAQUE");
                    System.out.println(movimiento.toString());
                }
                else if(resultSetMovimiento.getString("ESTADO") != null){
                    movimiento = new MovimientoEstado(
                            resultSetMovimiento.getString("NOM_MOVIMIENTO"),
                            resultSetMovimiento.getInt("PP_MAX"),
                            resultSetMovimiento.getInt("PP_REST"),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getString("ESTADO"),
                            resultSetMovimiento.getInt("TURNOS"),
                            resultSetMovimiento.getInt("ID_MOVIMIENTO")

                    );
                    System.out.println("SE HIZO UN MOVIMIENTO DE ESTADO");
                    System.out.println(movimiento.toString());
                }
                else if(resultSetMovimiento.getString("MEJORA") != null){
                    movimiento = new MovimientoMejora(
                            resultSetMovimiento.getString("NOM_MOVIMIENTO"),
                            resultSetMovimiento.getInt("PP_MAX"),
                            resultSetMovimiento.getInt("PP_REST"),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt("TURNOS"),
                            resultSetMovimiento.getString("MEJORA"),
                            resultSetMovimiento.getInt("CANT_MEJORA"),
                            resultSetMovimiento.getInt("ID_MOVIMIENTO")
                    );
                    System.out.println("SE HIZO UN MOVIMIENTO DE MEJORA");
                    System.out.println(movimiento.toString());
                }

                MOVIMIENTOS[i] = movimiento;
                System.out.println(MOVIMIENTOS[i].getNombre() + "Insertado en la posición " + i);
                i++;
            }

            resultSetMovimiento.close();
            statementMovimiento.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //HAY QUE AÑADIR LA LÓGICA DE LA BASE DE DATOS
    public void olvidarMovimiento(int indice){
        MOVIMIENTOS[indice] = null;
    }

    //Añade un movimiento aleatorio al array de 4 movimientos del pokemon.
    //Si el pokemon ya conoce ese movimiento, se repite el proceso.
    //Solo para pokemons generados aleatoriamente.
    public void setMovimiento(int indice){
        Movimiento nuevoMov;
        boolean repetirProceso = true;
        while (repetirProceso){
            nuevoMov = selecionarMovimientoDB();
            if (indice == 0) {
                MOVIMIENTOS[indice] = nuevoMov;
                repetirProceso = false;
            }
            else if (indice == 1
                    && nuevoMov.getIdMovimiento() != MOVIMIENTOS[indice-1].getIdMovimiento()){
                MOVIMIENTOS[indice] = nuevoMov;
                repetirProceso = false;
            }
            else if (indice == 2
                    && nuevoMov.getIdMovimiento() != MOVIMIENTOS[indice-1].getIdMovimiento()
                    && nuevoMov.getIdMovimiento() != MOVIMIENTOS[indice-2].getIdMovimiento()){
                MOVIMIENTOS[indice] = nuevoMov;
                repetirProceso = false;
            }
            else if (indice == 3
                    && nuevoMov.getIdMovimiento() != MOVIMIENTOS[indice-1].getIdMovimiento()
                    && nuevoMov.getIdMovimiento() != MOVIMIENTOS[indice-2].getIdMovimiento()
                    && nuevoMov.getIdMovimiento() != MOVIMIENTOS[indice-3].getIdMovimiento()){
                MOVIMIENTOS[indice] = nuevoMov;
                repetirProceso = false;
            }

        }
    }
    public Movimiento getMovimiento(int indice){
        return MOVIMIENTOS[indice];
    }

    //Método para generar aleatoriamente las estadísticas si se crea un pokemon directamente con un nivel mayor a 1 y
    //para usar en combate. Si el pokemon tiene nivel 1, el método no hace nada.
    public int subidaEstadisticasInstananea(int nivel){
        int estadistica = 0;
        for (int i = 1; i < nivel; i++) {
           estadistica += (int) ((Math.random()*5 + 1));
        }
        return estadistica;
    }

    public int nivelAleatorio(){
        int probabilidad = ((int) (Math.random()*100 + 1));
        if (probabilidad < 51) return 0;
        else {
            int sumaroRestar = (int) (Math.random()*2);
            if (sumaroRestar == 0){
                if (probabilidad >= 51 && probabilidad <= 80) return 1;
                else if (probabilidad >= 81 && probabilidad <= 95) return 2;
                else return 3;
            }
            else {
                if (probabilidad >= 51 && probabilidad <= 80) return -1;
                else if (probabilidad >= 81 && probabilidad <= 95) return -2;
                else return -3;
            }
        }
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

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getVitMax() {
        return vitMax;
    }

    public void setVitMax(int vitMax) {
        this.vitMax = vitMax;
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public int getFertilidad() {
        return fertilidad;
    }

    public void setFertilidad(int fertilidad) {
        this.fertilidad = fertilidad;
    }

    public int getNumPokedex() {
        return numPokedex;
    }

    public void setNumPokedex(int numPokedex) {
        this.numPokedex = numPokedex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return MOVIMIENTOS;
    }


    public EstadosPersitentes getEstadosPersistentes() {
        return estadosPersistentes;
    }

    public void setEstadosPersistentes(EstadosPersitentes estadosPersistentes) {
        this.estadosPersistentes = estadosPersistentes;
    }

    public EstadosTemporales getEstadosTemporales() {
        return estadosTemporales;
    }

    public void setEstadosTemporales(EstadosTemporales estadosTemporales) {
        this.estadosTemporales = estadosTemporales;
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
                ", numPokedex=" + numPokedex +
                ", id=" + id +
                ", tipo1=" + tipo1 +
                ", tipo2=" + tipo2 +
                ", MOVIMIENTOS=" + Arrays.toString(MOVIMIENTOS) +
                ", estadosPersistentes=" + estadosPersistentes +
                ", estadosTemporales=" + estadosTemporales +
                ", objetoEquipado=" + objetoEquipado +
                '}';
    }
}
