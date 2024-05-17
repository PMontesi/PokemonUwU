package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Combate {
    private ArrayList<Turno> turnos;
    private Entrenador usuario;
    private Entrenador rival;
    private Entrenador ganador = null;
    private int koJugador = 0;
    private int koRival = 0;
    private Pokemon primerPoke;
    private Pokemon segundoPoke;
    private int movimientoUsadoPrimer;
    private int MovimientoUsadoSegund;
    private Random r = new Random();

    public Combate(Entrenador usuario, Entrenador rival) {
        this.turnos = new ArrayList<>();
        this.usuario = usuario;
        this.rival = rival;
    }

    /**
     * Asigna quien ataca primero en base a quién tiene mayor prioridad.
     *
     * @param pokemonA uno de los pokemons del combate, generalmente el del usuario.
     * @param movimientoA el movimiento que usa el pokemonA
     * @param pokemonB uno de los pokemons del combate, generalmente el pokemon del rival.
     * @param movimientoB el movimiento que usa el pokemonB
     */
    public void combatir (Pokemon pokemonA, int movimientoA, Pokemon pokemonB, int movimientoB){
        setPrioridad(pokemonA, pokemonB);

        if (pokemonA.getPrioridad() > pokemonB.getPrioridad()){
            if(checkStun(pokemonA)){
                movimientoA = 5;
            }

            if(checkStun(pokemonB)){
                movimientoB = 5;
            }

            setPrimerPoke(pokemonA);
            setMovimientoUsadoPrimer(movimientoA);
            setSegundoPoke(pokemonB);
            setMovimientoUsadoSegund(movimientoB);
        }

        else if (pokemonA.getPrioridad() < pokemonB.getPrioridad()) {
            if(checkStun(pokemonA)){
                movimientoA = 5;
            }

            if(checkStun(pokemonB)){
                movimientoB = 5;
            }

            setPrimerPoke(pokemonB);
            setMovimientoUsadoPrimer(movimientoB);
            setSegundoPoke(pokemonA);
            setMovimientoUsadoSegund(movimientoA);
        }
    }

    /**
     * Asigna la prioridad a cada pokemon en base a su velocidad.
     * Tiene en cuenta los esados alterados que puedan alterar la velocidad
     *
     * @param pokemonA pokemonA uno de los pokemons del combate, generalmente el del usuario.
     * @param pokemonB pokemonB uno de los pokemons del combate, generalmente el pokemon del rival.
     */
    public void setPrioridad (Pokemon pokemonA, Pokemon pokemonB){
        int velPokeA = pokemonA.getVelocidad();
        if (pokemonA.getEstadosPersistentes() == EstPersitentesEnum.PARALIZADO
            || pokemonA.getEstadosPersistentes() == EstPersitentesEnum.DORMIDO){
            velPokeA *= 0.5;
        }

        int velPokeB = pokemonB.getVelocidad();
        if (pokemonB.getEstadosPersistentes() == EstPersitentesEnum.PARALIZADO
            || pokemonB.getEstadosPersistentes() == EstPersitentesEnum.DORMIDO) {
            velPokeB *= 0.5;
        }

        if (pokemonA.getVelocidad() > pokemonB.getVelocidad()){
            pokemonA.setPrioridad(pokemonA.getPrioridad()+1);
        }
        else if (pokemonB.getVelocidad() > pokemonA.getVelocidad()) {
            pokemonB.setPrioridad(pokemonA.getPrioridad()+1);
        }
        else if (pokemonA.getVelocidad() == pokemonB.getVelocidad()){
            int random = r.nextInt(2);
            if (random == 0){
                pokemonA.setPrioridad(pokemonA.getPrioridad()+1);
            }
            else {
                pokemonB.setPrioridad(pokemonA.getPrioridad()+1);
            }
        }
    }


    /**
     * Comprueba que el pokemon pueda o no atacar en base al estado que tenga.
     *
     * @param pokemon pokemon que sufre el estado alterado
     * @return verdadero si tiene uno de los estados (o supera el random que hace que resista el estado).
     */
    public boolean checkStun(Pokemon pokemon){
        return pokemon.getEstadosPersistentes() == EstPersitentesEnum.DORMIDO
                || pokemon.getEstadosPersistentes() == EstPersitentesEnum.CONGELADO
                || (pokemon.getEstadosPersistentes() == EstPersitentesEnum.PARALIZADO && (r.nextInt(4) + 1) != 1)
                || (pokemon.getEstadosPersistentes() == EstPersitentesEnum.SOMNOLIENTO && (r.nextInt(2) + 1) != 1)
                || (pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.ENAMORADO) && (r.nextInt(2) + 1) != 1)
                || pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.AMEDRENTADO);
    }

    /**
     * Determina el ganador del combate en base al número de ko's.
     *
     * @return devuelve el entrenador que ha ganado.
     */
    public Entrenador determinarGanador(){
        if(koJugador == 6) return rival;
        else if (koRival == 6)  return usuario;
        else return null;
    }


    //VER POR QUÉ NO VA ESTO EN EL ENTRENAMIENTO. POR CURIOSIDAD PORQUE ETRENAMIENTO NO TIENE QUE TENER ESTE MÉTODO

    /**
     * Entrega pokedólares al usuario según el nivel de los pokemons derrotados del rival.
     * Si el usuario ha perdido, se le resta 1/4 de los pokedolares del usuario.
     * En ambos casos, actualiza la base de datos.
     *
     * @param posNeg valor que indica si el usuario ha perdido o no
     * @param rival el rival del que sacar los pokemons.
     */
    public void entregarPokedolares(int posNeg, Entrenador rival){
        int pokedolaresEntregar = 0;
        if (posNeg == 1){
            for (Pokemon pokemon : rival.getEquipoPokemon()){
                pokedolaresEntregar += pokemon.getNivel()*100;
            }
        }
        else pokedolaresEntregar = -(usuario.getPokedolares()/4);

        usuario.setPokedolares(usuario.getPokedolares()+pokedolaresEntregar);

        String sqlAddPokedollars = "UPDATE ENTRENADOR SET POKEDOLLARS = POKEDOLLARS + ? WHERE ID_ENTRENADOR = ?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statementAddPokedollars = connection.prepareStatement(sqlAddPokedollars)){
            statementAddPokedollars.setInt(1, pokedolaresEntregar);
            statementAddPokedollars.setInt(2, usuario.getIdUsuario());
            statementAddPokedollars.executeUpdate();

            statementAddPokedollars.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *  Restaura el equipo pokemon sus estadísticas quitándole los aumentos por habilidades
     *  Este método será necesario revisarlo cuando se incluyan el centro pokemon y los objetos.
     */
    public void restaurarEquipo() {
        String sqlEquipoPokemon = "SELECT ATAQUE, AT_ESPECIAL, DEFENSA, DEF_ESPECIAL, VELOCIDAD FROM POKEMON_EQUIPO WHERE ID_ENTRENADOR = ? AND ID_POKEMON = ?;";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementEquipoPokemon = connection.prepareStatement(sqlEquipoPokemon)) {

            for (Pokemon pokemon: getUsuario().getEquipoPokemon()){
                if (pokemon != null){statementEquipoPokemon.setInt(1, usuario.getIdUsuario());
                    statementEquipoPokemon.setInt(2, pokemon.getId());
                    ResultSet resultSetEquipoPokemon = statementEquipoPokemon.executeQuery();

                    while (resultSetEquipoPokemon.next()) {
                        pokemon.setAtaque(resultSetEquipoPokemon.getInt("ATAQUE"));
                        pokemon.setAtaqueEspecial(resultSetEquipoPokemon.getInt("AT_ESPECIAL"));
                        pokemon.setDefensa(resultSetEquipoPokemon.getInt("DEFENSA"));
                        pokemon.setDefensaEspecial(resultSetEquipoPokemon.getInt("DEF_ESPECIAL"));
                        pokemon.setVelocidad(resultSetEquipoPokemon.getInt("VELOCIDAD"));
                        pokemon.setVitalidad(pokemon.getVitMax());
                    }
                    resultSetEquipoPokemon.close();
                }
            }


                statementEquipoPokemon.close();
                connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Entrega experiencia al pokemon del jugador que ha derrotado al pokemon rival.
     * Después, actualiza la base de datos.
     *
     * @param pokUsuario el pokemon que está usando el usuario en ese momento.
     * @param rival el pokemon dle rival derrotado en ese momento.
     */
    public void recibirExperiencia(Pokemon pokUsuario, Pokemon rival){
        int exp = rival.getNivel()*5;

           pokUsuario.setExperiencia(pokUsuario.getExperiencia() + exp);

            String sqlActualizarExp = "UPDATE POKEMON SET EXPERIENCIA = EXPERIENCIA + ? WHERE ID_POKEMON = ?";
            try(Connection connection = DBConnection.getConnection();
                PreparedStatement statementActualizarExp = connection.prepareStatement(sqlActualizarExp)){
                statementActualizarExp.setInt(1, exp);
                statementActualizarExp.setInt(2, pokUsuario.getId());
                statementActualizarExp.executeUpdate();

                statementActualizarExp.close();
                connection.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
    }


    public void crearLog(){
        //Lógica de crear log.
    }

    //GETTERS Y SETTERS
    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = turnos;
    }

    public Entrenador getUsuario() {
        return usuario;
    }

    public void setUsuario(Entrenador usuarior) {
        this.usuario = usuario;
    }

    public Entrenador getRival() {
        return rival;
    }

    public void setRival(Entrenador rival) {
        this.rival = rival;
    }

    public Entrenador getGanador() {
        return ganador;
    }

    public void setGanador(Entrenador ganador) {
        this.ganador = ganador;
    }

    public int getKoJugador() {
        return koJugador;
    }

    public void setKoJugador(int koJugador) {
        this.koJugador = koJugador;
    }

    public int getKoRival() {
        return koRival;
    }

    public void setKoRival(int koRival) {
        this.koRival = koRival;
    }

    public Pokemon getPrimerPoke() {
        return primerPoke;
    }

    public void setPrimerPoke(Pokemon primerPoke) {
        this.primerPoke = primerPoke;
    }

    public Pokemon getSegundoPoke() {
        return segundoPoke;
    }

    public void setSegundoPoke(Pokemon segundoPoke) {
        this.segundoPoke = segundoPoke;
    }

    public int getMovimientoUsadoPrimer() {
        return movimientoUsadoPrimer;
    }

    public void setMovimientoUsadoPrimer(int movimientoUsadoPrimer) {
        this.movimientoUsadoPrimer = movimientoUsadoPrimer;
    }

    public int getMovimientoUsadoSegund() {
        return MovimientoUsadoSegund;
    }

    public void setMovimientoUsadoSegund(int movimientoUsadoSegund) {
        MovimientoUsadoSegund = movimientoUsadoSegund;
    }

}
