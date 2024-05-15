package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class Pruebas {
    public static void main(String[] args) {

        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(r.nextInt(2));
        }







/*
ArrayList<turno> lista = new ArrayList<>();
Turno turno = new turno (1, 1, a, c);
lista.add(turno);
turno.setVariableB = 2;
turno.setVariableC = b;
turno.setVariableD = z;
lista.add(turno)
 */




       
       /* //public int stages(int stageLevel){}
        int stageLevel = 1;
        int stage = 2/2;
        if (stageLevel == 0){
            stage = 2/2;
        }
        else if (stageLevel == 1){
            stage = 3/2;
        }
        else if (stageLevel == 2){
            stage = 4/2;
        }
        else if (stageLevel == 3){
            stage = 5/2;
        }
        else if (stageLevel == 4){
            stage = 6/2;
        }
        else if (stageLevel == -1){
            stage = 2/3;
        }
        else if (stageLevel == -2){
            stage = 2/4;
        }
        else if (stageLevel == -3){
            stage = 2/5;
        }
        else if (stageLevel == -4){
            stage = 2/6;
        }

        int ataque = 10;
        int stageAT = ((int) 2/2);
        int defensa = 5;
        int stageDEF= ((int) 2/2);
        int damage = (ataque * stageAT) / (defensa * stageDEF);
        
        */

    }
}
