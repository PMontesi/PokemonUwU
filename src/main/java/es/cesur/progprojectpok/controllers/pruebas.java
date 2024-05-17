package es.cesur.progprojectpok.controllers;

import java.io.File;

public class pruebas {

    public static void main(String[] args) {

        float damageNivel = (float) (2 * 50) /5 + 2;
        float potencia = (float) 90 /100;
        float ataDef = (float) 200 /190;
        int prueba = (int) ((damageNivel * potencia * ataDef) + 2);
        int veneno = 20/16;
        System.out.println(veneno);
        System.out.println(prueba);




    }
}
