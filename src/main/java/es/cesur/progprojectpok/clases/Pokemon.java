package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
    private String imagenUrlDetras;
    private String imagenUrlDelante;
    private static final int VALOR_INICIAL_FERT = 5;
    private Tipos tipo1;
    private Tipos tipo2;
    private final Movimiento[] MOVIMIENTOS = new Movimiento[4];
    private EstPersitentesEnum estadosPersistentes;
    private int duracionPersistente;
    private ArrayList<EstTemporalesEnum> estTemporalesEnums;
    private int duracionConfusion;
    private int duracionCantoMortal = 3;
    private Objeto objetoEquipado;
    private Random r = new Random();

    private final String colNamePP_MAX = "PP_MAX";
    private final String colNameTipo_Dam = "TIPO_DAÑO";
    private final String colNameNOM_MOV = "NOM_MOVIMIENTO";
    private final String colNameID_MOV = "ID_MOVIMIENTO";
    private final String colNameESTADOS = "ESTADO";
    private final String colNameTURNOS = "TURNOS";
    private final String colNameMEJORA = "MEJORA";
    private final String colNamePP_REST = "PP_REST";

    public Pokemon() {
    }

    //Constructor de pokemons para capturarlos.
    public Pokemon(String nombre, int numPokedex, Tipos tipo1, Tipos tipo2) {
        this.nombre = nombre;
        this.vitalidad =    5 + r.nextInt(5) + 1;
        this.vitMax = this.vitalidad;
        this.ataque =           r.nextInt(5) + 1;
        this.defensa =          r.nextInt(5) + 1;
        this.ataqueEspecial =   r.nextInt(5) + 1;
        this.defensaEspecial =  r.nextInt(5) + 1;
        this.velocidad =        r.nextInt(5) + 1;
        this.nivel = 1;
        this.experiencia = 0;
        this.sexo = randomSex();
        this.numPokedex = numPokedex;
        this.fertilidad = VALOR_INICIAL_FERT;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
    }

    //Constructor para el combate pokemon. Principalmente para los pokemons del usuario.
    public Pokemon(String mote, int vitalidad, int vitMax, int ataque, int defensa, int ataqueEspecial, int defensaEspecial,
                   int velocidad, int nivel, int experiencia, int numPokedex, int id, String imagenUrlDetras, String imagenUrlDelante,
                   String tipo1, String tipo2, String estadosPersistentes, Objeto objetoEquipado) {
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
        this.imagenUrlDetras = imagenUrlDetras;
        this.imagenUrlDelante = imagenUrlDelante;
        this.tipo1 = Pokemon.TipoStringToEnum(tipo1);
        this.tipo2 = Pokemon.TipoStringToEnum(tipo2);
        this.estadosPersistentes = MovimientoEstado.estadosPersitentesStringtoEnum(estadosPersistentes);
        this.estTemporalesEnums = new ArrayList<>();
        this.objetoEquipado = objetoEquipado;
    }

    //Constructor para pokemons que no estén en la BBDD para usar en combate.
    //Por ahora solo para la vista combate (probablemente también para la de entrenamiento)
    public Pokemon(String nombre, int mediaNivel, int numPokedex, String imagenUrlDelante, String tipo1, String tipo2, Objeto objetoEquipado) {
        this.nombre = nombre;
        this.mote = nombre;
        this.nivel = mediaNivel + nivelAleatorio();
            if (this.nivel < 1) this.nivel = 1;
        this.ataque =           r.nextInt(10) + 1 + subidaEstadisticasInstananea(this.nivel);
        this.defensa =          r.nextInt(10) + 1 + subidaEstadisticasInstananea(this.nivel);
        this.ataqueEspecial =   r.nextInt(10) + 1 + subidaEstadisticasInstananea(this.nivel);
        this.defensaEspecial =  r.nextInt(10) + 1 + subidaEstadisticasInstananea(this.nivel);
        this.velocidad =        r.nextInt(10) + 1 + subidaEstadisticasInstananea(this.nivel);
        this.vitalidad =    5 + r.nextInt(10) + 1 + subidaEstadisticasInstananea(this.nivel);
        this.vitMax = this.vitalidad;
        this.sexo = randomSex();
        this.numPokedex = numPokedex;
        this.imagenUrlDetras = imagenUrlDelante;
        this.tipo1 = Pokemon.TipoStringToEnum(tipo1);
        this.tipo2 = Pokemon.TipoStringToEnum(tipo2);
        this.estadosPersistentes = EstPersitentesEnum.SALUDABLE;
        this.estTemporalesEnums = new ArrayList<>();
        this.objetoEquipado = objetoEquipado;
    }

    //Constructor para los tests de los movimientos. Solo tiene todos los atributos que intervienen en un movimiento
    public Pokemon(String nombre, int vitalidad, int vitMax, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad, int nivel, Tipos tipo1, Tipos tipo2) {
        this.nombre = nombre;
        this.vitalidad = vitalidad;
        this.vitMax = vitMax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
        this.nivel = nivel;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
    }

    public char randomSex(){
        char sex;
        int sexoBinario = r.nextInt(2);
        if (sexoBinario == 0) sex = 'H';
        else sex = 'M';
        return sex;
    }

    /**
     * Convierte el string en el enumerado correspondiente.
     * Principalmente para cuando se construye un pokemon.
     * @param tipoString el string obtenido de la base de datos
     * @return el enumerado correspondiente
     */
    public static Tipos TipoStringToEnum(String tipoString){
        if (tipoString == null){
            tipoString = "null";
        }
        return switch (tipoString.toUpperCase()) {
            case "AGUA" -> Tipos.AGUA;
            case "BICHO" -> Tipos.BICHO;
            case "DRAGÓN" -> Tipos.DRAGON;
            case "ELECTRICO" -> Tipos.ELECTRICO;
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

    /**
     * Convierte el enumerado en un string.
     * Principalmente para actualizar el pokemon en la base de datos.
     * @param tipoEnum el enumerado que tenga el pokemon
     * @return el enumerado en string
     */
    public static String TipoEnumToString(Tipos tipoEnum) {
        if (tipoEnum == null) {
            return "null";
        }
        return switch (tipoEnum) {
            case AGUA -> "AGUA";
            case BICHO -> "BICHO";
            case DRAGON -> "DRAGÓN";
            case ELECTRICO -> "ELECTRICO";
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

    /**
     * Otorga al pokemon estádisticas aleatorias entre 1 y 5 y resta experiencia el equivalente a el nivel del pokemon
     * por 10.
     * Después actualiza este pokemon enla base de datos.
     * @return verdadero si el nivel dle pokemon es múltiplo de 4, ya que a ese nivel tiene que aprender un movimiento
     */
    public boolean subirNivel(){
                int nuevoAT = (r.nextInt(5) + 1);
                int nuevoATESP = (r.nextInt(5)+ 1);
                int nuevoDEF = (r.nextInt(5) + 1);
                int nuevoDEFESP =  (r.nextInt(5) + 1);
                int nuevoVEL = (r.nextInt(5) + 1);
                int nuevoVITMAX =  (r.nextInt(5) + 1);
                int nuevaExp = getExperiencia() - nivel*10;

                setAtaque(getAtaque() + nuevoAT);
                setAtaqueEspecial(getAtaqueEspecial() + nuevoATESP);
                setDefensa(getDefensa() + nuevoDEF);
                setDefensaEspecial(getDefensaEspecial() + nuevoDEFESP);
                setVelocidad(getVelocidad() + nuevoVEL);
                setVitMax(getVitMax() + nuevoVITMAX);
                setVitalidad(getVitalidad() + nuevoVITMAX);
                setExperiencia(nuevaExp);
                setNivel(getNivel()+1);

            String sqlSubirNivel = "UPDATE POKEMON\n" +
                    "SET \n" +
                    "    ATAQUE = ATAQUE + ?, \n" +
                    "    AT_ESPECIAL = AT_ESPECIAL + ?, \n" +
                    "    DEFENSA = DEFENSA + ?, \n" +
                    "    DEF_ESPECIAL = DEF_ESPECIAL + ?, \n" +
                    "    VELOCIDAD = VELOCIDAD + ?,\n" +
                    "    VIT_MAX = VIT_MAX + ?, \n" +
                    "    EXPERIENCIA = ?, \n" +
                    "    NIVEL = NIVEL + 1 \n" +
                    "WHERE ID_POKEMON = ?";

            Connection connection = null;
            PreparedStatement statementSubirNivel = null;
            try{
                connection = DBConnection.getConnection();
                statementSubirNivel = connection.prepareStatement(sqlSubirNivel);
                statementSubirNivel.setInt(1, nuevoAT);
                statementSubirNivel.setInt(2, nuevoATESP);
                statementSubirNivel.setInt(3, nuevoDEF);
                statementSubirNivel.setInt(4, nuevoDEFESP);
                statementSubirNivel.setInt(5, nuevoVEL);
                statementSubirNivel.setInt(6, nuevoVITMAX);
                statementSubirNivel.setInt(7, nuevaExp);
                statementSubirNivel.setInt(8, getId());
                statementSubirNivel.executeUpdate();

            } catch(SQLException e){
                e.printStackTrace();
            } finally {
                try {
                    if (statementSubirNivel != null) {
                        statementSubirNivel.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        return nivel % 4 == 0;
    }


    /**
     * Selecciona un movimiento aleatorio de la base de datos que el pokemon pueda aprender y no conozca ya.
     * Un movimiento es aprendible por un pokemon según el tipo del pokemon y del nivel que tenga.
     * Los movimientos de tipo NORMAL son aprendibles por todos los pokemons.
     * Una vez que se ha aprendido el movimiento, según si es un movimiento de Ataque, Mejora o Estado, se usará un constructor
     * diferente.
     */
    public Movimiento selecionarMovimientoDB(){
        Movimiento movimiento = null;
        Connection connection = null;
        PreparedStatement statementMovimiento = null;
        ResultSet resultSetMovimiento = null;

        String sqlMovimiento = "SELECT * FROM MOVIMIENTOS M \n" +
                "WHERE NIVEL_APRENDIZAJE <= ? \n" +
                "AND TIPO = 'NORMAL'\n" +
                "OR TIPO = ?\n" +
                "OR TIPO = ?\n" +
                "AND NOT EXISTS (\n" +
                "    SELECT *\n" +
                "    FROM MOVIMIENTOS_POKEMON MP\n" +
                "    WHERE MP.ID_MOVIMIENTO = M.ID_MOVIMIENTO AND\n" +
                "    MP.ID_POKEMON = ?\n" +
                ")\n" +
                "ORDER BY RAND() LIMIT 1";

        try{
            connection = DBConnection.getConnection();
            statementMovimiento = connection.prepareStatement(sqlMovimiento);


                statementMovimiento.setInt(1, getNivel());
                statementMovimiento.setString(2, TipoEnumToString(getTipo1()));
                statementMovimiento.setString(3, TipoEnumToString(getTipo2()));
                statementMovimiento.setInt(4, getId());

            resultSetMovimiento = statementMovimiento.executeQuery();
            while (resultSetMovimiento.next()){
                if(resultSetMovimiento.getString(colNameTipo_Dam) != null){
                    movimiento = new MovimientoAtaque(
                            resultSetMovimiento.getString(colNameNOM_MOV),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt("POTENCIA"),
                            resultSetMovimiento.getString(colNameTipo_Dam),
                            resultSetMovimiento.getInt(colNameID_MOV)
                    );
                }
                else if(resultSetMovimiento.getString(colNameESTADOS) != null){
                    movimiento = new MovimientoEstado(
                            resultSetMovimiento.getString(colNameNOM_MOV),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getString(colNameESTADOS),
                            resultSetMovimiento.getInt(colNameTURNOS),
                            resultSetMovimiento.getInt(colNameID_MOV)
                    );
                }
                else if(resultSetMovimiento.getString(colNameMEJORA) != null){
                    movimiento = new MovimientoMejora(
                            resultSetMovimiento.getString(colNameNOM_MOV),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt(colNameTURNOS),
                            resultSetMovimiento.getString(colNameMEJORA),
                            resultSetMovimiento.getInt("CANT_MEJORA"),
                            resultSetMovimiento.getInt(colNameID_MOV)
                    );
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (resultSetMovimiento != null) {
                    resultSetMovimiento.close();
                }
                try {
                    if (statementMovimiento != null) {
                        statementMovimiento.close();
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return movimiento;
    }

    /**
     * Método para que los pokemons del entrenador aprendan movimientos.
     * Primero asigna el movimiento al hueco vacío del array.
     * Segundo actualiza la base de datos, insertando la ID en la tabla correspondiente
     * y poniendo el valor de la columna ACTIVO en 1.
     *
     * @param movimiento el movimiento el cual se tiene que aprender.
     */
    public void aprenderMovimiento(Movimiento movimiento){
        String sqlAprender = ("INSERT INTO MOVIMIENTOS_POKEMON (ID_MOVIMIENTO, ID_POKEMON, ACTIVO) VALUES (?, ?, ?)");
        int aprender = 0;

        for (int i = 0; i < MOVIMIENTOS.length; i++) {
            if (MOVIMIENTOS[i] == null) {
                MOVIMIENTOS[i] = movimiento;
                aprender = 1;
                break;
            }
        }

        try{
            Connection connection = DBConnection.getConnection();
            PreparedStatement statementAprender = connection.prepareStatement(sqlAprender);
            statementAprender.setInt(1, movimiento.getIdMovimiento());
            statementAprender.setInt(2, getId());
            statementAprender.setInt(3, aprender);
            statementAprender.executeUpdate();
            statementAprender.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Filtra los movimientos para llamar el método correspondiente, según el tipo de movimiento.
    public void usarMovimiento(int indice, Pokemon pokemonObjetivo, Pokemon pokemonCaster){
        if (indice == 5){
            MovimientoAtaque movNopega = new MovimientoAtaque("No pudo pegar", 0, 0,Tipos.NULL, 0, "FISICO");
            movNopega.aplicarDamage(pokemonObjetivo, pokemonCaster);
        }
        else {
            try{
                if (MOVIMIENTOS[indice] instanceof MovimientoAtaque ataqueMov){
                    ataqueMov.aplicarDamage(pokemonObjetivo, pokemonCaster);
                }
                else if (MOVIMIENTOS[indice] instanceof MovimientoMejora mejoraMov){
                    mejoraMov.mejoraAplica(pokemonCaster);
                }
                else if (MOVIMIENTOS[indice] instanceof MovimientoEstado estadoMov){
                    estadoMov.aplicarEstado(pokemonObjetivo, ((MovimientoEstado) MOVIMIENTOS[indice]).getEstado());
                }
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }


    }

    /**
     * Recupera y asigna los movimientos disponibles para un Pokémon dado su ID.
     * Selecciona los movimientos activos asociados al Pokémon desde la base de datos,
     * y los instancia en objetos Movimiento correspondientes para su posterior uso.
     *
     * @param pokemonId El identificador del Pokémon para el cual se asignarán los movimientos.
     */
    public void asignarMovimientos (int pokemonId){
        Connection connection = null;
        PreparedStatement statementMovimiento = null;
        ResultSet resultSetMovimiento = null;

        String sqlMovimiento = "SELECT M.*, MD.PP_REST \n" +
                "FROM MOVIMIENTOS M\n" +
                "INNER JOIN MOVIMIENTOS_POKEMON MD ON MD.ID_MOVIMIENTO = M.ID_MOVIMIENTO\n" +
                "INNER JOIN POKEMON P ON P.ID_POKEMON = MD.ID_POKEMON\n" +
                "WHERE P.ID_POKEMON = ?\n" +
                "AND MD.ACTIVO = 1;";
        Movimiento movimiento = null;
        int i = 0;
        try{
            connection = DBConnection.getConnection();
            statementMovimiento = connection.prepareStatement(sqlMovimiento);
            statementMovimiento.setInt(1, pokemonId);
            resultSetMovimiento = statementMovimiento.executeQuery();
            while (resultSetMovimiento.next()){
                if(resultSetMovimiento.getString(colNameTipo_Dam) != null){
                    movimiento = new MovimientoAtaque(
                            resultSetMovimiento.getString(colNameNOM_MOV),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            resultSetMovimiento.getInt(colNamePP_REST),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt("POTENCIA"),
                            resultSetMovimiento.getString(colNameTipo_Dam),
                            resultSetMovimiento.getInt(colNameID_MOV)
                    );

                }
                else if(resultSetMovimiento.getString(colNameESTADOS) != null){
                    movimiento = new MovimientoEstado(
                            resultSetMovimiento.getString(colNameNOM_MOV),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            resultSetMovimiento.getInt(colNamePP_REST),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getString(colNameESTADOS),
                            resultSetMovimiento.getInt(colNameTURNOS),
                            resultSetMovimiento.getInt(colNameID_MOV)

                    );

                }
                else if(resultSetMovimiento.getString(colNameMEJORA) != null){
                    movimiento = new MovimientoMejora(
                            resultSetMovimiento.getString(colNameNOM_MOV),
                            resultSetMovimiento.getInt(colNamePP_MAX),
                            resultSetMovimiento.getInt(colNamePP_REST),
                            TipoStringToEnum(resultSetMovimiento.getString("TIPO")),
                            0,
                            resultSetMovimiento.getInt(colNameTURNOS),
                            resultSetMovimiento.getString(colNameMEJORA),
                            resultSetMovimiento.getInt("CANT_MEJORA"),
                            resultSetMovimiento.getInt(colNameID_MOV)
                    );

                }

                MOVIMIENTOS[i] = movimiento;
                i++;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSetMovimiento != null) {
                    resultSetMovimiento.close();
                }
                if (statementMovimiento != null) {
                    statementMovimiento.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Cambia el activo del movimiento en la base de datos a 0 y después lo quita del array
     *
     * @param indiceMovimiento la posición del movimiento en el array del pokemon.
     */
    public void olvidarMovimiento(int indiceMovimiento){
        String sqlOlvidarMovimiento = "UPDATE MOVIMIENTOS_POKEMON SET ACTIVO = 0 WHERE ID_POKEMON = ? AND ID_MOVIMIENTO = ?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statementOlvidarMovimiento = connection.prepareStatement(sqlOlvidarMovimiento)){
            statementOlvidarMovimiento.setInt(1, getId());
            statementOlvidarMovimiento.setInt(2, getMovimiento(indiceMovimiento).getIdMovimiento());
            statementOlvidarMovimiento.executeUpdate();

            statementOlvidarMovimiento.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        MOVIMIENTOS[indiceMovimiento] = null;
    }


    /**
     * Añade un movimiento aleatorio al array de 4 movimientos del pokemon.
     * Si el pokemon ya conoce ese movimiento, se repite el proceso.
     * Si no lo conoce, se inserta en su array.
     * Si el pokemon es un pokemon del usuario, este método se usa cuando el pokemon quiere aprender un movimiento.
     * Si el pokemon es uno generado para pelear, solo sirve para añadir movimientos a su array.
     *
     * @param indice posición del movimiento que se quiere aprender del array de movimientos del pokemon.
     */
    public void setMovimiento(int indice){
        Movimiento nuevoMov = selecionarMovimientoDB();

        boolean movimientoRepetido = false;

        for (int i = 0; i < indice; i++) {
                if (nuevoMov.getIdMovimiento() == MOVIMIENTOS[i].getIdMovimiento()) {
                    movimientoRepetido = true;
                    break;
                }
            }

        if (!movimientoRepetido){
            MOVIMIENTOS[indice] = nuevoMov;
            if ( getId()!= 0){
                aprenderMovimiento(nuevoMov);
            }
        }
        else setMovimiento(indice);
    }

    public Movimiento getMovimiento(int indice){
        try{
            return MOVIMIENTOS[indice];
        }catch (ArrayIndexOutOfBoundsException e){
            return new Movimiento("nada", 0, 0, Tipos.NULL, 0,  0 );
        }
    }



    /**
     * Generar aleatoriamente las estadísticas si se crea un pokemon directamente con un nivel mayor a 1 y
     * para usar en combate. Si el pokemon tiene nivel 1, el método no hace nada.
     *
     * @param nivel nivel del pokemon
     * @return devuelve la cantidad de estadística a aumentar.
     */
    public int subidaEstadisticasInstananea(int nivel){
        int estadistica = 0;
        for (int i = 1; i < nivel; i++) {
           estadistica += r.nextInt(5) + 1;
        }
        return estadistica;
    }

    //Mëtodo para aumentar el nivel aleatoriamente de los pokemons que se generan en combate

    /**
     * Aumenta o disminuye el nivel de los pokemons antes de empezar un combate.
     * Se usa exclusivamente para pokemons rivales generados aleatoriamente para el combate
     *
     * @return la cantidad de nivel que tiene que aumentarse o disminuirse
     */
    public int nivelAleatorio(){
        int probabilidad = r.nextInt(100) + 1;
        if (probabilidad < 51) return 0;
        else {
            int sumaroRestar = r.nextInt(2);
            int nivelAdicional = 0;

            if (probabilidad <= 80) {
                nivelAdicional = 1;
            } else if (probabilidad <= 95) {
                nivelAdicional = 2;
            } else {
                nivelAdicional = 3;
            }

            return (sumaroRestar == 0) ? nivelAdicional : -nivelAdicional;
        }
    }

    /**
     * Devuelve un string que es la ruta absoluta de la imagen del pokemon
     *
     * @param url la url de la imagen en el proyecto
     * @return la url teniendo en cuenta donde está el proyecto en el dispositivo.
     */
    public static String imgRutaAbsouta(String url){
        File archivo = new File(url);
        String rutaAbsoluta = archivo.getAbsolutePath();
        if(System.getProperty("os.name").startsWith("Windows")){
            rutaAbsoluta = rutaAbsoluta.replace("/", "\\");
        }
        return rutaAbsoluta;
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

    public String getImagenUrlDetras() {
        return imagenUrlDetras;
    }

    public void setImagenUrlDetras(String imagenUrlDetras) {
        this.imagenUrlDetras = imagenUrlDetras;
    }

    public String getImagenUrlDelante() {
        return imagenUrlDelante;
    }

    public void setImagenUrlDelante(String imagenUrlDelante) {
        this.imagenUrlDelante = imagenUrlDelante;
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

    public EstPersitentesEnum getEstadosPersistentes() {
        return estadosPersistentes;
    }

    public void setEstadosPersistentes(EstPersitentesEnum estadosPersistentes) {
        this.estadosPersistentes = estadosPersistentes;
    }

    public ArrayList<EstTemporalesEnum> getEstTemporalesEnums() {
        return estTemporalesEnums;
    }

    public void setEstTemporal(EstTemporalesEnum estadoTemporal){
        estTemporalesEnums.add(estadoTemporal);
    }

    public Objeto getObjetoEquipado() {
        return objetoEquipado;
    }

    public void setObjetoEquipado(Objeto objetoEquipado) {
        this.objetoEquipado = objetoEquipado;
    }

    public int getDuracionPersistente() {
        return duracionPersistente;
    }

    public void setDuracionPersistente(int duracionPersistente) {
        this.duracionPersistente = duracionPersistente;
    }

    public int getDuracionConfusion() {
        return duracionConfusion;
    }

    public void setDuracionConfusion(int duracionConfusion) {
        this.duracionConfusion = duracionConfusion;
    }

    public int getDuracionCantoMortal() {
        return duracionCantoMortal;
    }

    public void setDuracionCantoMortal(int duracionCantoMortal) {
        this.duracionCantoMortal = duracionCantoMortal;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Pokemon{" +
                "nombre='" + nombre + '\'' +
                ", mote='" + mote + '\'' +
                ", vitalidad=" + vitalidad +
                ", vitMax=" + vitMax +
                ", ataque=" + ataque +
                ", defensa=" + defensa +
                ", ataqueEspecial=" + ataqueEspecial +
                ", defensaEspecial=" + defensaEspecial +
                ", velocidad=" + velocidad +
                ", prioridad=" + prioridad +
                ", nivel=" + nivel +
                ", experiencia=" + experiencia +
                ", sexo=" + sexo +
                ", fertilidad=" + fertilidad +
                ", numPokedex=" + numPokedex +
                ", id=" + id +
                ", imagenUrl='" + imagenUrlDetras + '\'' +
                ", tipo1=" + tipo1 +
                ", tipo2=" + tipo2 +
                ", MOVIMIENTOS=" + Arrays.toString(MOVIMIENTOS) +
                ", estadosPersistentes=" + estadosPersistentes +
                ", duracionPersistente=" + duracionPersistente +
                ", estTemporalesEnums=" + estTemporalesEnums +
                ", duracionConfusion=" + duracionConfusion +
                ", duracionCantoMortal=" + duracionCantoMortal +
                ", objetoEquipado=" + objetoEquipado +
                ", random=" + r +
                '}';
    }
}
